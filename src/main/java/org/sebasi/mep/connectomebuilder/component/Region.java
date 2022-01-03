package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.generator.ClusterSpec;
import org.sebasi.mep.connectomebuilder.generator.RegionSpec;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.ArrayList;
import java.util.List;

public class Region extends AbstractComponent {

    byte rid;
    List<Cluster> clusters;

    public Region(
            byte rid,
            RegionSpec regionSpec) {

        this.rid = rid;

        List<ClusterSpec> clusterSpecList = regionSpec.getClusterSpecList();
        if (clusterSpecList.size() > NidUtil.MAX_NUM_CLUSTERS_PER_REGION) {
            throw new RuntimeException("Too many clusters in region rid=" + NidUtil.convertRidToHexString(rid)
                    + ". Found " + clusterSpecList.size()
                    + " but max is " + NidUtil.MAX_NUM_CLUSTERS_PER_REGION + ".");
        }

        clusters = new ArrayList<>(clusterSpecList.size());
        for (short cid = 0; cid < clusterSpecList.size(); cid++) {
            clusters.add(
                    new Cluster(
                            cid,
                            clusterSpecList.get(cid)));
        }
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of clusters: ").append(clusters.size());
    }
}
