package tud.stg.analysis;

import java.io.UTFDataFormatException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.wala.cfg.ControlFlowGraph;
import com.ibm.wala.cfg.cdg.ControlDependenceGraph;
import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.dataflow.graph.BitVectorSolver;
import com.ibm.wala.shrikeBT.IInvokeInstruction.Dispatch;
import com.ibm.wala.shrikeBT.InstanceofInstruction;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.ISSABasicBlock;
import com.ibm.wala.ssa.SSAArrayLengthInstruction;
import com.ibm.wala.ssa.SSAArrayLoadInstruction;
import com.ibm.wala.ssa.SSAArrayStoreInstruction;
import com.ibm.wala.ssa.SSABinaryOpInstruction;
import com.ibm.wala.ssa.SSACheckCastInstruction;
import com.ibm.wala.ssa.SSAComparisonInstruction;
import com.ibm.wala.ssa.SSAConditionalBranchInstruction;
import com.ibm.wala.ssa.SSAConversionInstruction;
import com.ibm.wala.ssa.SSAGetCaughtExceptionInstruction;
import com.ibm.wala.ssa.SSAGetInstruction;
import com.ibm.wala.ssa.SSAGotoInstruction;
import com.ibm.wala.ssa.SSAInstanceofInstruction;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAInvokeInstruction;
import com.ibm.wala.ssa.SSALoadMetadataInstruction;
import com.ibm.wala.ssa.SSAMonitorInstruction;
import com.ibm.wala.ssa.SSANewInstruction;
import com.ibm.wala.ssa.SSAPhiInstruction;
import com.ibm.wala.ssa.SSAPiInstruction;
import com.ibm.wala.ssa.SSAPutInstruction;
import com.ibm.wala.ssa.SSAReturnInstruction;
import com.ibm.wala.ssa.SSASwitchInstruction;
import com.ibm.wala.ssa.SSAThrowInstruction;
import com.ibm.wala.ssa.SSAUnaryOpInstruction;
import com.ibm.wala.ssa.analysis.ExplodedControlFlowGraph;
import com.ibm.wala.ssa.analysis.IExplodedBasicBlock;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.collections.Filter;
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;
import com.ibm.wala.util.graph.traverse.DFSAllPathsFinder;
import com.ibm.wala.util.strings.Atom;
import com.ibm.wala.util.strings.StringStuff;

public class StreamCloseVisitor extends SSAInstruction.Visitor {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(StreamCloseVisitor.class);
  private Map<Integer, SSAInvokeInstruction> map = new HashMap<Integer, SSAInvokeInstruction>();
  private Set<MethodReference> testSet = new HashSet<MethodReference>();
  private final ExplodedControlFlowGraph ecfg;
  private final ControlDependenceGraph<SSAInstruction, ISSABasicBlock> cdg;
  Graph<SSAInstruction> graph = SlowSparseNumberedGraph.make();
  private int result = 0;

  // public SQLInjectionVisitor(IR ir, ExplodedControlFlowGraph ecfg,
  // BitVectorSolver<IExplodedBasicBlock> solver) {
  // testSet = new HashSet<MethodReference>();
  // map = new HashMap<Integer, SSAInvokeInstruction>();
  // }

  public StreamCloseVisitor(ExplodedControlFlowGraph ecfg, ControlDependenceGraph<SSAInstruction, ISSABasicBlock> cdg) {
    this.ecfg = ecfg;
    this.cdg = cdg;
    logger.setLevel(Level.INFO);
    testSet = new HashSet<MethodReference>();
    map = new HashMap<Integer, SSAInvokeInstruction>();
    graph = SlowSparseNumberedGraph.make();
    result = 0;
  }

  @Override
  public void visitInvoke(SSAInvokeInstruction instruction) {
    logger.debug("VISITOR " + instruction);

    for (int def = 0; def < instruction.getNumberOfDefs(); def++) {
      map.put(instruction.getDef(def), instruction);
    }

    Dispatch invocationCode = (Dispatch) instruction.getInvocationCode();
    switch (invocationCode) {
    case SPECIAL:
      break;
    case VIRTUAL:
      break;
    case INTERFACE:

      break;
    case STATIC:

      break;
    default:
      logger.fatal("WRONG INVOCATION TYPE IN :" + instruction);
      break;
    }
  }

  /**
   * @param instruction
   */
  private void getPredessesors(SSAInvokeInstruction instruction) {
    for (int use = 0; use < instruction.getNumberOfUses(); use++) {
      if (map.containsKey(instruction.getUse(use))) {
        SSAInvokeInstruction e = map.get(instruction.getUse(use));
        MethodReference declaredTarget = e.getCallSite().getDeclaredTarget();
        logger.debug("SIGNATURE: " + declaredTarget.getSignature());
        testSet.add(declaredTarget);
        getPredessesors(e);
      }
    }
  }

  public int result() {

    return result;
  }

  public void visitGoto(SSAGotoInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitArrayLoad(SSAArrayLoadInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitArrayStore(SSAArrayStoreInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitBinaryOp(SSABinaryOpInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitUnaryOp(SSAUnaryOpInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitConversion(SSAConversionInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitComparison(SSAComparisonInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitConditionalBranch(SSAConditionalBranchInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitSwitch(SSASwitchInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitReturn(SSAReturnInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitGet(SSAGetInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitPut(SSAPutInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitNew(SSANewInstruction instruction) {
    logger.debug("VISITOR " + instruction);

    try {
      if (instruction.getNewSite().getDeclaredType().getName().getClassName().toUnicodeString().contains("Stream")) {
        int index = ecfg.getIR().getBasicBlockForInstruction(instruction).getFirstInstructionIndex();
        IExplodedBasicBlock blockForInstruction = ecfg.getBlockForInstruction(index);

        getSuccessor(blockForInstruction, instruction);
        DFSAllPathsFinder<SSAInstruction> allPathsFinder = new DFSAllPathsFinder<>(graph, instruction,
            new Filter<SSAInstruction>() {

              @Override
              public boolean accepts(SSAInstruction o) {
                if (o instanceof SSAReturnInstruction) {
                  return true;
                }
                return false;
              }
            });

        List<SSAInstruction> find = allPathsFinder.find();
        MethodReference input = StringStuff.makeMethodReference("java.io.InputStream.close()V;");
        MethodReference output = StringStuff.makeMethodReference("java.io.OutputStream.close()V;");
        while (find != null) {
          boolean close = false;
          for (SSAInstruction ssaInstruction : find) {
            if (ssaInstruction instanceof SSAInvokeInstruction) {
              SSAInvokeInstruction invoke = (SSAInvokeInstruction) ssaInstruction;
              if (invoke.getCallSite().getDeclaredTarget().equals(input) || invoke.getCallSite().getDeclaredTarget().equals(output)) {
                close = true;
                break;
              }
            }
          }
          if (!close) {
            result++;
          }
          find = allPathsFinder.find();
        }
      }
    } catch (UTFDataFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @param blockForInstruction
   */
  private void getSuccessor(IExplodedBasicBlock blockForInstruction, SSAInstruction lastValid) {
    SSAInstruction instruction = blockForInstruction.getInstruction();
    if (instruction != null) {
      graph.addNode(lastValid);
      graph.addNode(instruction);
      graph.addEdge(lastValid, instruction);
      lastValid = instruction;
    }

    Iterator<IExplodedBasicBlock> succNodes = ecfg.getSuccNodes(blockForInstruction);
    while (succNodes.hasNext()) {
      IExplodedBasicBlock iExplodedBasicBlock = (IExplodedBasicBlock) succNodes.next();
      getSuccessor(iExplodedBasicBlock, lastValid);
    }
  }

  public void visitArrayLength(SSAArrayLengthInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitThrow(SSAThrowInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitMonitor(SSAMonitorInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitCheckCast(SSACheckCastInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitInstanceof(SSAInstanceofInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitPhi(SSAPhiInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitPi(SSAPiInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitGetCaughtException(SSAGetCaughtExceptionInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  public void visitLoadMetadata(SSALoadMetadataInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

}
