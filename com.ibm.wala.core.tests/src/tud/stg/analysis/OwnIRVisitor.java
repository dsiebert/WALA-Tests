package tud.stg.analysis;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Iterator;

import com.ibm.wala.analysis.reflection.JavaLangClassContextInterpreter;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.propagation.HeapModel;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.callgraph.propagation.PointerKey;
import com.ibm.wala.ipa.callgraph.propagation.SSAContextInterpreter;
import com.ibm.wala.ipa.slicer.NormalStatement;
import com.ibm.wala.ipa.slicer.Slicer;
import com.ibm.wala.ipa.slicer.Slicer.ControlDependenceOptions;
import com.ibm.wala.ipa.slicer.Slicer.DataDependenceOptions;
import com.ibm.wala.ipa.slicer.Statement.Kind;
import com.ibm.wala.ipa.slicer.Statement;
import com.ibm.wala.ssa.IR;
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
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.intset.OrdinalSet;

public class OwnIRVisitor extends SSAInstruction.Visitor {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(OwnIRVisitor.class);

  private final CallGraph cg;
  private final PointerAnalysis pa;

  public OwnIRVisitor(CallGraph cg, PointerAnalysis pa) {
    logger.setLevel(Level.DEBUG);
    BasicConfigurator.configure();
    this.cg = cg;
    this.pa = pa;
  }

  @Override
  public void visitGoto(SSAGotoInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitArrayLoad(SSAArrayLoadInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitArrayStore(SSAArrayStoreInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitBinaryOp(SSABinaryOpInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitUnaryOp(SSAUnaryOpInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitConversion(SSAConversionInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitComparison(SSAComparisonInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitConditionalBranch(SSAConditionalBranchInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitSwitch(SSASwitchInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitReturn(SSAReturnInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitGet(SSAGetInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitPut(SSAPutInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitInvoke(SSAInvokeInstruction instruction) {
    logger.debug("VISITOR " + instruction);
    logger.debug(instruction.getInvocationCode());
//    logger.debug(instruction.getDeclaredTarget().getNumberOfParameters());
//    logger.debug(instruction.getInvocationCode());
//    if(instruction.getInvocationCode().(ClassLoaderReference.Application)){
    HeapModel heapModel = pa.getHeapModel();
    logger.debug("INSTANCE KEY FOR "+heapModel.getInstanceKeyForClassObject(instruction.getDeclaredResultType()));

//    while (iteratePointerKeys.hasNext()) {
//      PointerKey pointerKey = (PointerKey) iteratePointerKeys.next();
//      OrdinalSet<InstanceKey> pointsToSet = pa.getPointsToSet(pointerKey);
//      for (InstanceKey instanceKey : pointsToSet) {
//        logger.debug("INSTANCE KEY "+ instanceKey);
//        logger.debug("CONCRETE TYPE" +instanceKey.getConcreteType());
//      }
//    }
    
    
  }

  @Override
  public void visitNew(SSANewInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitArrayLength(SSAArrayLengthInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitThrow(SSAThrowInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitMonitor(SSAMonitorInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitCheckCast(SSACheckCastInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitInstanceof(SSAInstanceofInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitPhi(SSAPhiInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitPi(SSAPiInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitGetCaughtException(SSAGetCaughtExceptionInstruction instruction) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visitLoadMetadata(SSALoadMetadataInstruction instruction) {
    // TODO Auto-generated method stub

  }

}
