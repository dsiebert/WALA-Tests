package tud.stg.analysis;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.TypeReference;

public class ExceptionVisitor extends SSAInstruction.Visitor {
  private static final Logger logger = Logger.getLogger(ExceptionVisitor.class);
  private int result;
  private final IR ir;
  
  public ExceptionVisitor(IR ir) {
   this.ir = ir;
  logger.setLevel(Level.DEBUG);
  }
  @Override
  public void visitGoto(SSAGotoInstruction instruction) {
   logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitArrayLoad(SSAArrayLoadInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitArrayStore(SSAArrayStoreInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitBinaryOp(SSABinaryOpInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitUnaryOp(SSAUnaryOpInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitConversion(SSAConversionInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitComparison(SSAComparisonInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitConditionalBranch(SSAConditionalBranchInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitSwitch(SSASwitchInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitReturn(SSAReturnInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitGet(SSAGetInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitPut(SSAPutInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitInvoke(SSAInvokeInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitNew(SSANewInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitArrayLength(SSAArrayLengthInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitThrow(SSAThrowInstruction instruction) {
   Collection<TypeReference> exceptionTypes = instruction.getExceptionTypes();
   TypeReference exception = TypeReference.findOrCreate(ClassLoaderReference.Primordial, "Ljava/lang/Exception");
   if(exceptionTypes.size() == 1 ){
     for (TypeReference typeReference : exceptionTypes) {
       if (typeReference.equals(exception)) {
         result++;
       }
    }
   }
  }

  @Override
  public void visitMonitor(SSAMonitorInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitCheckCast(SSACheckCastInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitInstanceof(SSAInstanceofInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitPhi(SSAPhiInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitPi(SSAPiInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }

  @Override
  public void visitGetCaughtException(SSAGetCaughtExceptionInstruction instruction) {
    logger.debug("CAUGHT " + instruction);
    ISSABasicBlock basicBlockForCatch = ir.getBasicBlockForCatch(instruction);
    Iterator<TypeReference> caughtExceptionTypes = basicBlockForCatch.getCaughtExceptionTypes();
    while (caughtExceptionTypes.hasNext()) {
      TypeReference typeReference = (TypeReference) caughtExceptionTypes.next();
      System.err.println();
    }
    TypeReference exception = TypeReference.findOrCreate(ClassLoaderReference.Application, "Ljava/lang/Exception");
   
    
  }

  @Override
  public void visitLoadMetadata(SSALoadMetadataInstruction instruction) {
    logger.debug("VISITOR " + instruction);
  }


  public int result(){
    return result;
  }
}
