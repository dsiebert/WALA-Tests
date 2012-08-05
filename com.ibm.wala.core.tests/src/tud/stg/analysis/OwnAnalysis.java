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
import java.io.UTFDataFormatException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.classLoader.NewSiteReference;
import com.ibm.wala.dataflow.IFDS.BackwardsSupergraph;
import com.ibm.wala.dataflow.IFDS.PathEdge;
import com.ibm.wala.dataflow.IFDS.TabulationResult;
import com.ibm.wala.dataflow.IFDS.TabulationSolver;
import com.ibm.wala.examples.analysis.dataflow.ContextSensitiveReachingDefs;
import com.ibm.wala.examples.drivers.PDFTypeHierarchy;
import com.ibm.wala.examples.properties.WalaExamplesProperties;
import com.ibm.wala.fixedpoint.impl.DefaultFixedPointSolver;
import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.CallGraphBuilderCancelException;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.DefaultEntrypoint;
import com.ibm.wala.ipa.callgraph.impl.Everywhere;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.LocalPointerKey;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.cfg.BasicBlockInContext;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.ipa.slicer.Slicer;
import com.ibm.wala.ipa.slicer.Slicer.ControlDependenceOptions;
import com.ibm.wala.ipa.slicer.Slicer.DataDependenceOptions;
import com.ibm.wala.ipa.slicer.Statement;
import com.ibm.wala.ipa.slicer.thin.ThinSlicer;
import com.ibm.wala.properties.WalaProperties;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAOptions;
import com.ibm.wala.ssa.analysis.IExplodedBasicBlock;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.Descriptor;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.Selector;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.collections.CollectionFilter;
import com.ibm.wala.util.collections.Filter;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.debug.Assertions;
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.graph.GraphSlicer;
import com.ibm.wala.util.strings.Atom;
import com.ibm.wala.viz.DotUtil;
import com.ibm.wala.viz.PDFViewUtil;

/**
 * This is a simple example WALA application.
 * 
 * This counts the number of parameters to each method in the primordial loader
 * (the J2SE standard libraries), and prints the result.
 * 
 * @author sfink
 */
public class OwnAnalysis {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(OwnAnalysis.class);

  private final static ClassLoader MY_CLASSLOADER = OwnAnalysis.class.getClassLoader();

  private static CGNode master;

  private static CallGraph cg;

  private static PointerAnalysis pa;

  
  /**
   * Use the 'CountParameters' launcher to run this program with the appropriate
   * classpath
   */
  public static void main(String[] args) throws IOException, ClassHierarchyException {
    logger.setLevel(Level.DEBUG);
    BasicConfigurator.configure();

    String file = args[0];
    // build an analysis scope representing the standard libraries, excluding no
    // classes
    AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(file, new File(
        "E:\\Git\\WALA-Tests\\com.ibm.wala.core.tests\\dat\\OwnExclustion.txt"));

    // build a class hierarchy
    logger.info("Build class hierarchy...", null); //$NON-NLS-1$
    IClassHierarchy cha = ClassHierarchy.make(scope);
    logger.info("Done", null); //$NON-NLS-1$

    //
    int nClasses = 0;
    int nMethods = 0;
    for (IClass c : cha) {
      if (!c.getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) {
        nClasses++;
//        logger.debug(c.getAllMethods());
        for (IMethod m : c.getDeclaredMethods()) {
          Collection<IMethod> possibleTargets = cha.getPossibleTargets(m.getReference());
          for (IMethod iMethod : possibleTargets) {
//            logger.debug("Possible Targets of " + m.getName() + " --> " + iMethod.getName());

          }
          nMethods++;
//          logger.debug("Reference " + m.getReference());
//          logger.debug("Reference " + m.getSignature());
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(nClasses + " classes"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    if (logger.isDebugEnabled()) {
      logger.debug(nMethods + " methods"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    TypeReference typeRef = TypeReference.findOrCreate(ClassLoaderReference.Application, "LsimpleTest/ToDo");
    MethodReference methodReference = MethodReference.findOrCreate(typeRef, Selector.make("doSomething(Ljava/lang/String;)V"));

    /*
     * ENTRYPOINTS
     */
    Iterable<Entrypoint> allApplicationEntrypoints = new AllApplicationEntrypoints(scope, cha);
    Iterable<Entrypoint> makeMainEntrypoints = Util.makeMainEntrypoints(scope, cha);
//    DefaultEntrypoint defaultEntrypoint = new DefaultEntrypoint(methodReference, cha);
//    HashSet<Entrypoint> entrypoints = new HashSet<>();
//    entrypoints.add(defaultEntrypoint);

    AnalysisOptions options = new AnalysisOptions(scope, makeMainEntrypoints);
    options.getSSAOptions().setPiNodePolicy(SSAOptions.getAllBuiltInPiNodes());
    // // //
    // // build the call graph
    // // //
    try {
      AnalysisCache cache = new AnalysisCache();
      CallGraphBuilder builder = Util.makeZeroOneContainerCFABuilder(options, cache, cha, scope);
      cg = builder.makeCallGraph(options, null);
      pa = builder.getPointerAnalysis();

      for (CGNode cgNode : cg) {
        if (!cgNode.getMethod().getDeclaringClass().getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) {
          logger.debug("\n");
          logger.debug("METHOD: " + cgNode.getMethod().getName());
//          logger.debug("Context: " + cgNode.getContext());
//          IR ir = cache.getSSACache().findOrCreateIR(cgNode.getMethod(), Everywhere.EVERYWHERE, options.getSSAOptions());
//          ir.visitAllInstructions(new OwnIRVisitor(cg, pa));

          if (cgNode.getMethod().getName().toUnicodeString().equals("doSomething")) {
            master = cgNode;
          }
//
//          Iterator<SSAInstruction> iterateAllInstructions = ir.iterateAllInstructions();
//          // while (iterateAllInstructions.hasNext()) {
//          // SSAInstruction ssaInstruction = (SSAInstruction)
//          // iterateAllInstructions.next();
//          // logger.debug(ssaInstruction);
//          // // logger.debug("Is Def: " +ssaInstruction.hasDef());
//          // //
//          // logger.debug("Number of Defs: "+ssaInstruction.getNumberOfDefs());
//          // //
//          // logger.debug("Number of Uses: "+ssaInstruction.getNumberOfUses());
//          // }
//          Iterator<CallSiteReference> iterateCallSites = ir.iterateCallSites();
//          while (iterateCallSites.hasNext()) {
//            CallSiteReference callSiteReference = (CallSiteReference) iterateCallSites.next();
//            // logger.debug("Call Site " +
//            // callSiteReference.getDeclaredTarget());
          

        }

      }


      // CGNode mainMethod = findMainMethod(cg);
      //
      // find seed statement
      Statement statement = findCallTo(master, "executeQuery");
      Collection<Statement> slice;
      slice = makeSlice(cg, pa, statement);
//      for (Statement statement2 : slice) {
//        if(statement2.getKind().equals(Statement.Kind.NORMAL_RET_CALLER)){
//        logger.debug("Type: " +statement2.getKind());
//        logger.debug("Call Sites");
//        Iterator<CallSiteReference> iterateCallSites = statement.getNode().iterateCallSites();
//        while (iterateCallSites.hasNext()) {
//          CallSiteReference callSiteReference = (CallSiteReference) iterateCallSites.next();       
//          logger.debug(callSiteReference);
//          
//        }
//       
//        
//        logger.debug("New Sites");
//        Iterator<NewSiteReference> iterateNewSites = statement.getNode().iterateNewSites();
//        while (iterateNewSites.hasNext()) {
//          NewSiteReference newSiteReference = (NewSiteReference) iterateNewSites.next();       
//          logger.debug(newSiteReference);
//        }
//        makeSlice(cg, pa, statement2);
//      }
//      }
      
//      //
//      logger.debug("");
//      // // context-insensitive slice
//      ThinSlicer ts = new ThinSlicer(cg, pa);
//      slice = ts.computeBackwardThinSlice(statement);
//      dumpSlice(slice);
      // AnalysisCache cache = new AnalysisCache();
      // ContextSensitiveReachingDefs reachingDefs = new
      // ContextSensitiveReachingDefs(cg, cache);
      // TabulationResult<BasicBlockInContext<IExplodedBasicBlock>, CGNode,
      // Pair<CGNode, Integer>> result = reachingDefs.analyze();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CallGraphBuilderCancelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CancelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


//    ContextSensitiveReachingDefs reachingDefs = new ContextSensitiveReachingDefs(cg, new AnalysisCache());
//
//    TabulationResult<BasicBlockInContext<IExplodedBasicBlock>, CGNode, Pair<CGNode, Integer>> tabResult = reachingDefs.analyze();
//
//    Collection<BasicBlockInContext<IExplodedBasicBlock>> supergraphNodesReached = tabResult.getSupergraphNodesReached();
//    for (BasicBlockInContext<IExplodedBasicBlock> basicBlockInContext : supergraphNodesReached) {
//      if(!basicBlockInContext.getMethod().getDeclaringClass().getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) {
//
//       Iterator<SSAInstruction> iterator = basicBlockInContext.iterator();
//       while (iterator.hasNext()) {
//        SSAInstruction ssaInstruction = (SSAInstruction) iterator.next();
//        logger.debug("SSA INSTRUCTION SET");
//        logger.debug(ssaInstruction);
//
//      )
//       CGNode node = basicBlockInContext.getNode();
//       logger.debug("RETURN POINTS TO: "+pa.getHeapModel().getPointerKeyForReturnValue(node));
//      }
      
    
    
    
    
//    Collection<PathEdge<BasicBlockInContext<IExplodedBasicBlock>>> seeds = tabResult.getSeeds();
//    for (PathEdge<BasicBlockInContext<IExplodedBasicBlock>> pathEdge : seeds) {
//      if(!pathEdge.getEntry().getMethod().getDeclaringClass().getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) logger.debug(pathEdge);
//    }
//    
//    Collection<PathEdge<BasicBlockInContext<IExplodedBasicBlock>>> initialSeeds = tabResult.getProblem().initialSeeds();
//    for (PathEdge<BasicBlockInContext<IExplodedBasicBlock>> pathEdge : initialSeeds) {
//      if(!pathEdge.getEntry().getMethod().getDeclaringClass().getClassLoader().getReference().equals(ClassLoaderReference.Primordial)) logger.debug(pathEdge);
//    }

    
    //
    // ISupergraph<BasicBlockInContext<IExplodedBasicBlock>, CGNode> supergraph
    // = reachingDefs.getSupergraph();
    // Graph<CGNode> procedureGraph = (Graph<CGNode>)
    // supergraph.getProcedureGraph();
    // for (CGNode cgNode : procedureGraph) {
    // if(cgNode.getClass().getClassLoader().equals(ClassLoaderReference.Primordial)){
    // logger.debug(cgNode.getClass().getClassLoader());
    // }
    //
    // }
    //
    // logger.debug("supergraph number = " + supergraph.getMaxNumber());
    //
    // for (BasicBlockInContext<IExplodedBasicBlock> bb : supergraph) {
    // if(bb.getClass().getClassLoader().equals(ClassLoaderReference.Primordial)){
    // logger.debug(bb.getClass().getClassLoader());
    // }
    // }
    // } catch (IllegalArgumentException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (CallGraphBuilderCancelException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

  }

  private static Collection<Statement> makeSlice(CallGraph cg, PointerAnalysis pa, Statement statement) throws CancelException,
      UTFDataFormatException {
    Collection<Statement> slice;
    slice = Slicer.computeBackwardSlice(statement, cg, pa, DataDependenceOptions.FULL,
        ControlDependenceOptions.FULL);
    dumpSlice(slice);
    return slice;
  }

  public static CGNode findMainMethod(CallGraph cg) {
    Descriptor d = Descriptor.findOrCreateUTF8("([Ljava/lang/String;)V");
    Atom name = Atom.findOrCreateUnicodeAtom("main");
    for (Iterator<? extends CGNode> it = cg.getSuccNodes(cg.getFakeRootNode()); it.hasNext();) {
      CGNode n = it.next();
      if (n.getMethod().getName().equals(name) && n.getMethod().getDescriptor().equals(d)) {
        return n;
      }
    }
    Assertions.UNREACHABLE("failed to find main() method");
    return null;
  }

  public static Statement findCallTo(CGNode n, String methodName) {
    IR ir = n.getIR();
    for (Iterator<SSAInstruction> it = ir.iterateAllInstructions(); it.hasNext();) {
      SSAInstruction s = it.next();
      if (s instanceof com.ibm.wala.ssa.SSAAbstractInvokeInstruction) {
        com.ibm.wala.ssa.SSAAbstractInvokeInstruction call = (com.ibm.wala.ssa.SSAAbstractInvokeInstruction) s;
        if (call.getCallSite().getDeclaredTarget().getName().toString().equals(methodName)) {
          com.ibm.wala.util.intset.IntSet indices = ir.getCallInstructionIndices(call.getCallSite());
          com.ibm.wala.util.debug.Assertions.productionAssertion(indices.size() == 1, "expected 1 but got " + indices.size());
          return new com.ibm.wala.ipa.slicer.NormalStatement(n, indices.intIterator().next());
        }
      }
    }
    Assertions.UNREACHABLE("failed to find call to " + methodName + " in " + n);
    return null;
  }

  public static void dumpSlice(Collection<Statement> slice) throws UTFDataFormatException, CancelException {
    for (Statement s : slice) {
      logger.debug("SLICE STATEMENT: " +s);
    }
  }

  private static void createPDF(Graph<CGNode> g) throws WalaException {

    Properties p = null;
    try {
      p = WalaExamplesProperties.loadProperties();
      p.putAll(WalaProperties.loadProperties());
    } catch (WalaException e) {
      e.printStackTrace();
      Assertions.UNREACHABLE();
    }
    logger.info("Creating PDF");

    String PDF_FILE = "cg.pdf";
    String pdfFile = p.getProperty(WalaProperties.OUTPUT_DIR) + File.separatorChar + PDF_FILE;
    String dotExe = p.getProperty(WalaExamplesProperties.DOT_EXE);
    DotUtil.dotify(g, null, PDFTypeHierarchy.DOT_FILE, pdfFile, dotExe);
    String gvExe = p.getProperty(WalaExamplesProperties.PDFVIEW_EXE);

    logger.info("Finishe PDF creation");
    PDFViewUtil.launchPDFView(pdfFile, gvExe);
  }

  public static <T> Graph<T> pruneGraph(Graph<T> g, Filter<T> f) throws WalaException {
    Collection<T> slice = GraphSlicer.slice(g, f);
    return GraphSlicer.prune(g, new CollectionFilter<T>(slice));
  }

  /**
   * Restrict g to nodes from the Application loader
   */
  public static Graph<IClass> pruneForAppLoader(Graph<IClass> g) throws WalaException {
    Filter<IClass> f = new Filter<IClass>() {
      public boolean accepts(IClass c) {
        return (c.getClassLoader().getReference().equals(ClassLoaderReference.Application));
      }
    };
    return pruneGraph(g, f);
  }

  private static class ApplicationLoaderFilter implements Filter<CGNode> {

    public boolean accepts(CGNode o) {
      if (o instanceof CGNode) {
        CGNode n = (CGNode) o;
        return n.getMethod().getDeclaringClass().getClassLoader().getReference().equals(ClassLoaderReference.Application);
      } else if (o instanceof LocalPointerKey) {
        LocalPointerKey l = (LocalPointerKey) o;
        return accepts(l.getNode());
      } else {
        return false;
      }
    }
  }
}
