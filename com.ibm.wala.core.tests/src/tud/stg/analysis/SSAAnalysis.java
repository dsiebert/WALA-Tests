/*******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package tud.stg.analysis;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.JarFile;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.wala.cfg.ControlFlowGraph;
import com.ibm.wala.cfg.cdg.ControlDependenceGraph;
import com.ibm.wala.classLoader.IBytecodeMethod;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.classLoader.ShrikeIRFactory;
import com.ibm.wala.dataflow.IFDS.PathEdge;
import com.ibm.wala.dataflow.IFDS.TabulationDomain;
import com.ibm.wala.dataflow.IFDS.TabulationProblem;
import com.ibm.wala.dataflow.IFDS.TabulationResult;
import com.ibm.wala.dataflow.graph.BitVectorSolver;
import com.ibm.wala.examples.analysis.dataflow.ContextSensitiveReachingDefs;
import com.ibm.wala.examples.analysis.dataflow.IntraprocReachingDefs;
import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.CallGraphBuilderCancelException;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.Everywhere;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.cfg.BasicBlockInContext;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.shrikeBT.IInstruction;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.ssa.DefaultIRFactory;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.IRFactory;
import com.ibm.wala.ssa.ISSABasicBlock;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAOptions;
import com.ibm.wala.ssa.analysis.ExplodedControlFlowGraph;
import com.ibm.wala.ssa.analysis.IExplodedBasicBlock;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.debug.Assertions;
import com.ibm.wala.util.intset.IntSet;
import com.ibm.wala.util.strings.Atom;
import com.ibm.wala.util.strings.StringStuff;

/**
 * This is a simple example WALA application.
 * 
 * This counts the number of parameters to each method in the primordial loader
 * (the J2SE standard libraries), and prints the result.
 * 
 * @author Dennis Siebert
 */
public class SSAAnalysis {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(SSAAnalysis.class);
  private static int i = 0;

  /**
   * Use the 'CountParameters' launcher to run this program with the appropriate
   * classpath
   * 
   * @throws CallGraphBuilderCancelException
   * @throws IllegalArgumentException
   * @throws InvalidClassFileException
   */
  public static void main(String[] args) throws IOException, ClassHierarchyException, IllegalArgumentException,
      CallGraphBuilderCancelException, InvalidClassFileException {
    logger.setLevel(Level.INFO);
    BasicConfigurator.configure();

    String file = args[0];

    // build an analysis scope representing the standard libraries and our
    // application
    AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(file, new File(
        "E:\\Git\\WALA-Tests\\com.ibm.wala.core.tests\\dat\\OwnExclustion.txt"));
    scope.addToScope(ClassLoaderReference.Primordial, new JarFile(new File("E:\\Git\\WALA-Tests\\servlet-api.jar")));

    logger.info("Created AnalysisScope");
    ShrikeIRFactory shrikeIRFactory = new ShrikeIRFactory();
    AnalysisOptions options = new AnalysisOptions();
    options.getSSAOptions().setPiNodePolicy(SSAOptions.getAllBuiltInPiNodes());

    IClassHierarchy cha = ClassHierarchy.make(scope);

    logger.info("Iterate thru classes");
    for (IClass c : cha) {

      if (!c.getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) {
        logger.info("Handling Class: " + c.getName());
        for (IMethod m : c.getDeclaredMethods()) {
          if (m.getReference().getName().equals(Atom.findOrCreateUnicodeAtom("<init>"))) {
            continue;
          }
          IR ir = new DefaultIRFactory().makeIR(m, Everywhere.EVERYWHERE, SSAOptions.defaultOptions());
          ExplodedControlFlowGraph ecfg = ExplodedControlFlowGraph.make(ir);
          IBytecodeMethod bytecodeMethod = (IBytecodeMethod) ecfg.getIR().getMethod();
          IInstruction[] bInstruction = bytecodeMethod.getInstructions();
          ControlDependenceGraph<SSAInstruction, ISSABasicBlock> cdg = new ControlDependenceGraph<SSAInstruction, ISSABasicBlock>(
              ir.getControlFlowGraph());

          i = 0;
          for (IInstruction iInstruction : bInstruction) {
            logger.debug(i++ + " " + iInstruction);
          }

          // IntraprocReachingDefs reachingDefs = new
          // IntraprocReachingDefs(ecfg, cha);

          SSAInstruction[] instructions = ir.getInstructions();
          i = 0;

          for (SSAInstruction ssaInstruction : instructions) {
            if (ssaInstruction == null) {
              logger.debug(i++ + " null");
            } else {
              logger.debug(i++ + " " + ssaInstruction + " DEFS: " + ssaInstruction.getNumberOfDefs() + " USES: "
                  + ssaInstruction.getNumberOfUses());

            }
          }

          // analysis / verification part
          SQLInjectionVisitor sqlCheck = new SQLInjectionVisitor(ir);
          StreamCloseVisitor streamClosed = new StreamCloseVisitor(ecfg, cdg);
          ExceptionVisitor exceptionVisitor = new ExceptionVisitor(ir);

          ir.visitAllInstructions(sqlCheck);
          logger.info("Result for SQL Injection in " + m.getName() + " = " + sqlCheck.result());
          ir.visitAllInstructions(streamClosed);
          logger.info("Result for Streams closed incorrect in " + m.getName() + " = " + streamClosed.result());

          ir.visitAllInstructions(exceptionVisitor);
          logger.info("Result for throwing to general exceptions in " +m.getName() + " = "+exceptionVisitor.result());

          
          /*
           * TOP LEVEL should catch own exceptions
           */
          String doGet = "doGet(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";
          String doPost = "doPost(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";
          TypeReference httpServlet = TypeReference
              .findOrCreate(ClassLoaderReference.Primordial, "Ljavax/servlet/http/HttpServlet");
          if ((m.getSignature().contains(doPost) || m.getSignature().contains(doGet))
              && m.getDeclaringClass().getSuperclass().getReference().equals(httpServlet)) {
            TypeReference[] declaredExceptions = m.getDeclaredExceptions();
            if (declaredExceptions.length != 0)
              logger.info("Result for Top-Level Method " + m.getName() + " is not handling an error by itself");
          }
          /*
           * Too  Exceptions thrown
           */
          TypeReference[] declaredExceptions = m.getDeclaredExceptions();
          if (declaredExceptions.length == 1) {
            TypeReference exception = TypeReference.findOrCreate(ClassLoaderReference.Application, "Ljava/lang/Exception");
            for (TypeReference typeReference : declaredExceptions) {
              if (typeReference.equals(exception)) {
                logger.info("Method " + m.getName() + " is throwing a too general exception");
              }
            }
          }

          logger.info("");
        }
      }
    }
    System.exit(0);
  }

}
