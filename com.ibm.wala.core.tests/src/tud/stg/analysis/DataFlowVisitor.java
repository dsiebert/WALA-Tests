package tud.stg.analysis;

import java.util.HashMap;
import java.util.Map;

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
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;
import com.ibm.wala.util.graph.labeled.SlowSparseNumberedLabeledGraph;

public class DataFlowVisitor extends  SSAInstruction.Visitor {

 SlowSparseNumberedGraph<Integer> graph = SlowSparseNumberedGraph.make();
 private Map<Integer, SSAInstruction> map = new HashMap<Integer, SSAInstruction>();
private IR ir;
  
  public DataFlowVisitor(IR ir) {
    this.ir = ir;
  // TODO Auto-generated constructor stub
}

  @Override
  public void visitGoto(SSAGotoInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitArrayLoad(SSAArrayLoadInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitArrayStore(SSAArrayStoreInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitBinaryOp(SSABinaryOpInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitUnaryOp(SSAUnaryOpInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitConversion(SSAConversionInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitComparison(SSAComparisonInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitConditionalBranch(SSAConditionalBranchInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitSwitch(SSASwitchInstruction instruction) {
      System.out.println(instruction);
  }

  @Override
  public void visitReturn(SSAReturnInstruction instruction) {
    System.out.println(graph);
  }

  @Override
  public void visitGet(SSAGetInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitPut(SSAPutInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitInvoke(SSAInvokeInstruction instruction) {
    System.out.println(instruction);
    for (int def = 0; def < instruction.getNumberOfDefs(); def++) {
      map.put(instruction.getDef(def), instruction);
      graph.addNode(instruction.getDef(def));
    }
    
    for(int use =0; use < instruction.getNumberOfUses(); use++){
//      Object constantValue = ir.getSymbolTable().getConstantValue(instruction.getUse(use));
//      System.out.println(constantValue);
      if(graph.containsNode(instruction.getUse(use)) && instruction.hasDef()){
        graph.addEdge(instruction.getUse(use), instruction.getDef());
      }
    }
    
  }

  @Override
  public void visitNew(SSANewInstruction instruction) {
    System.out.println(instruction);
    for (int def = 0; def < instruction.getNumberOfDefs(); def++) {
      map.put(instruction.getDef(def), instruction);
      graph.addNode(instruction.getDef(def));
    }
   
  }

  @Override
  public void visitArrayLength(SSAArrayLengthInstruction instruction) {
  System.out.println(instruction);

  }

  @Override
  public void visitThrow(SSAThrowInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitMonitor(SSAMonitorInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitCheckCast(SSACheckCastInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitInstanceof(SSAInstanceofInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitPhi(SSAPhiInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitPi(SSAPiInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitGetCaughtException(SSAGetCaughtExceptionInstruction instruction) {
    System.out.println(instruction);
  }

  @Override
  public void visitLoadMetadata(SSALoadMetadataInstruction instruction) {
    System.out.println(instruction);
  }

 }
