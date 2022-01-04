package org.sebasi.mep.connectomebuilder.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sebasi.mep.connectomebuilder.generator.ClusterSpec;
import org.sebasi.mep.connectomebuilder.generator.RegionSpec;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.List;

public class Region extends AbstractComponent {

    byte rid;
    int numClusters;

    public Region(
            byte rid,
            int numClusters) {
        this.rid = rid;
        this.numClusters = numClusters;
    }

    public void writeInitialDataFiles(
            String einDirectory,
            RegionSpec regionSpec) {

        List<ClusterSpec> clusterSpecList = regionSpec.getClusterSpecList();
        if (clusterSpecList.size() > NidUtil.MAX_NUM_CLUSTERS_PER_REGION) {
            throw new RuntimeException("Too many clusters in region rid=" + NidUtil.convertRidToHexString(rid)
                    + ". Found " + clusterSpecList.size()
                    + " but max is " + NidUtil.MAX_NUM_CLUSTERS_PER_REGION + ".");
        }
        if (clusterSpecList.size() != numClusters) {
            throw new RuntimeException("Number of clusters in cluster creation ("
                    + numClusters + ") doesn't match numebr of clusters in spec (" + clusterSpecList.size() + ").");
        }

        try {
            FileUtil.writeRegion(
                    einDirectory,
                    GlobalStaticHelper.objectMapper.writeValueAsString(this),
                    rid);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem serializing region into json: " + e.getMessage(), e);
        }

        for (short cid = 0; cid < clusterSpecList.size(); cid++) {
            new Cluster(rid, cid)
                    .writeInitialDataFiles(
                            einDirectory,
                            clusterSpecList.get(cid));
        }
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of clusters: ").append(numClusters);
    }
}
