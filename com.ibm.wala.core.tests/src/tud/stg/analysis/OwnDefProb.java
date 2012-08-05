package tud.stg.analysis;

import java.util.Collection;

import com.ibm.wala.dataflow.IFDS.IMergeFunction;
import com.ibm.wala.dataflow.IFDS.IPartiallyBalancedFlowFunctions;
import com.ibm.wala.dataflow.IFDS.ISupergraph;
import com.ibm.wala.dataflow.IFDS.PartiallyBalancedTabulationProblem;
import com.ibm.wala.dataflow.IFDS.PathEdge;
import com.ibm.wala.dataflow.IFDS.TabulationDomain;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.cfg.BasicBlockInContext;
import com.ibm.wala.ssa.analysis.IExplodedBasicBlock;
import com.ibm.wala.util.collections.Pair;

public class OwnDefProb implements
PartiallyBalancedTabulationProblem<BasicBlockInContext<IExplodedBasicBlock>, CGNode, Pair<CGNode, Integer>>  {

  @Override
  public ISupergraph<BasicBlockInContext<IExplodedBasicBlock>, CGNode> getSupergraph() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TabulationDomain<Pair<CGNode, Integer>, BasicBlockInContext<IExplodedBasicBlock>> getDomain() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<PathEdge<BasicBlockInContext<IExplodedBasicBlock>>> initialSeeds() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMergeFunction getMergeFunction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IPartiallyBalancedFlowFunctions<BasicBlockInContext<IExplodedBasicBlock>> getFunctionMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BasicBlockInContext<IExplodedBasicBlock> getFakeEntry(BasicBlockInContext<IExplodedBasicBlock> n) {
    // TODO Auto-generated method stub
    return null;
  }

}
