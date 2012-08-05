package tud.stg.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.dataflow.graph.BitVectorSolver;
import com.ibm.wala.shrikeBT.IInvokeInstruction.Dispatch;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAInvokeInstruction;
import com.ibm.wala.ssa.Value;
import com.ibm.wala.ssa.analysis.ExplodedControlFlowGraph;
import com.ibm.wala.ssa.analysis.IExplodedBasicBlock;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.util.strings.Atom;
import com.ibm.wala.util.strings.StringStuff;

public class SQLInjectionVisitor extends SSAInstruction.Visitor {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(SQLInjectionVisitor.class);
  private Map<Integer, SSAInvokeInstruction> map = new HashMap<Integer, SSAInvokeInstruction>();
  private Set<MethodReference> testSet = new HashSet<MethodReference>();
  private final IR ir;

//  public SQLInjectionVisitor(IR ir, ExplodedControlFlowGraph ecfg, BitVectorSolver<IExplodedBasicBlock> solver) {
//    testSet = new HashSet<MethodReference>();
//    map = new HashMap<Integer, SSAInvokeInstruction>();
//  }

  public SQLInjectionVisitor(IR ir) {
    this.ir = ir;
    logger.setLevel(Level.DEBUG);
    testSet = new HashSet<MethodReference>();
    map = new HashMap<Integer, SSAInvokeInstruction>();
  }

  @Override
  public void visitInvoke(SSAInvokeInstruction instruction) {
    logger.debug("VISITOR " + instruction + " DEF " + instruction.getDef());

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
      if (instruction.getCallSite().getDeclaredTarget().getName().equals(Atom.findOrCreateAsciiAtom("executeQuery"))) {
        getPredessesors(instruction);
        for (MethodReference ssa : testSet) {
          logger.debug(ssa.getName());
        }
      }
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
        logger.debug("SIGNATURE: " +declaredTarget.getSignature());
        testSet.add(declaredTarget);
        getPredessesors(e);
      }
    }
  }

  public int result() {

   MethodReference append = StringStuff.makeMethodReference("java.lang.StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;");
   MethodReference getParameter = StringStuff.makeMethodReference("javax.servlet.http.HttpServletRequest.getParameter(Ljava/lang/String;)Ljava/lang/String;");

    if (testSet.contains(append) && testSet.contains(getParameter))
      return 1;

    return 0;
  }

}
