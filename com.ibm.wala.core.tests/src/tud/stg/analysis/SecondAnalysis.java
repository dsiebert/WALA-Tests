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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.wala.cfg.ControlFlowGraph;
import com.ibm.wala.cfg.cdg.ControlDependenceGraph;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.dataflow.IFDS.PathEdge;
import com.ibm.wala.dataflow.IFDS.TabulationDomain;
import com.ibm.wala.dataflow.IFDS.TabulationProblem;
import com.ibm.wala.dataflow.IFDS.TabulationResult;
import com.ibm.wala.examples.analysis.dataflow.ContextSensitiveReachingDefs;
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
import com.ibm.wala.ssa.DefaultIRFactory;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.ISSABasicBlock;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAOptions;
import com.ibm.wala.ssa.analysis.IExplodedBasicBlock;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.debug.Assertions;

/**
 * This is a simple example WALA application.
 * 
 * This counts the number of parameters to each method in the primordial loader
 * (the J2SE standard libraries), and prints the result.
 * 
 * @author sfink
 */
public class SecondAnalysis {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(SecondAnalysis.class);

  private final static ClassLoader MY_CLASSLOADER = SecondAnalysis.class.getClassLoader();

  private static CGNode master;

  private static PointerAnalysis pa;

  private static ControlFlowGraph makeCFG;

  private static ControlDependenceGraph<SSAInstruction, ISSABasicBlock> cdg;

  /**
   * Use the 'CountParameters' launcher to run this program with the appropriate
   * classpath
   * @throws CallGraphBuilderCancelException 
   * @throws IllegalArgumentException 
   */
  public static void main(String[] args) throws IOException, ClassHierarchyException, IllegalArgumentException, CallGraphBuilderCancelException {
    logger.setLevel(Level.DEBUG);
    BasicConfigurator.configure();

    String file = args[0];
    
    // build an analysis scope representing the standard libraries and our application
    AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(file, new File(
        "F:\\WALA\\WALA\\com.ibm.wala.core.tests\\dat\\OwnExclustion.txt"));
    logger.debug("Created AnalysisScope");
    DefaultIRFactory defaultIRFactory = new DefaultIRFactory();
    
    IClassHierarchy cha = ClassHierarchy.make(scope);
    for (IClass c : cha) {
      if (!c.getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) {
        for (IMethod m : c.getDeclaredMethods()) {
        
        }
      }
    }
    Iterable<Entrypoint> entrypoints = Util.makeMainEntrypoints(scope, cha);
    AnalysisOptions options = new AnalysisOptions(scope, entrypoints);

    CallGraphBuilder builder = Util.makeZeroCFABuilder(options, new AnalysisCache(), cha, scope);
    CallGraph cg = builder.makeCallGraph(options, null);
    
    AnalysisCache cache = new AnalysisCache();
    ContextSensitiveReachingDefs reachingDefs = new ContextSensitiveReachingDefs(cg, cache);
    TabulationResult<BasicBlockInContext<IExplodedBasicBlock>, CGNode, Pair<CGNode, Integer>> result = reachingDefs.analyze();
    
    Collection<PathEdge<BasicBlockInContext<IExplodedBasicBlock>>> seeds = result.getSeeds();
    Collection<BasicBlockInContext<IExplodedBasicBlock>> supergraphNodesReached = result.getSupergraphNodesReached();
    TabulationProblem<BasicBlockInContext<IExplodedBasicBlock>, CGNode, Pair<CGNode, Integer>> problem = result.getProblem();
    TabulationDomain<Pair<CGNode, Integer>, BasicBlockInContext<IExplodedBasicBlock>> domain = problem.getDomain();
    Collection<PathEdge<BasicBlockInContext<IExplodedBasicBlock>>> initialSeeds = problem.initialSeeds();

    System.exit(0);
  }

  /**
   * @param m
   * @return 
   */
  private static ControlDependenceGraph<SSAInstruction, ISSABasicBlock> createCDG(IMethod m) {
    AnalysisOptions options = new AnalysisOptions();
    options.getSSAOptions().setPiNodePolicy(SSAOptions.getAllBuiltInPiNodes());
    AnalysisCache cache = new AnalysisCache();
    IR ir = cache.getSSACache().findOrCreateIR(m, Everywhere.EVERYWHERE, options.getSSAOptions() );

    if (ir == null) {
      Assertions.UNREACHABLE("Null IR for " + m);
    }

    System.err.println(ir.toString());
    ControlDependenceGraph<SSAInstruction, ISSABasicBlock> cdg = new ControlDependenceGraph<SSAInstruction, ISSABasicBlock>(ir.getControlFlowGraph());
    return cdg;
  }
}
