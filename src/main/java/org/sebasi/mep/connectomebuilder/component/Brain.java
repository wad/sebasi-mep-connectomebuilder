package org.sebasi.mep.connectomebuilder.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.generator.RegionSpec;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.List;

public class Brain extends AbstractComponent {

    int numRegions;

    public Brain(int numRegions) {
        this.numRegions = numRegions;
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of regions: ").append(numRegions);
    }

    public void writeInitialDataFiles(
            String einDirectory,
            ConnectomeGenSpec cgs) {

        List<RegionSpec> regionSpecList = cgs.getRegionSpecList();
        if (regionSpecList.size() > NidUtil.MAX_NUM_REGIONS) {
            throw new RuntimeException("Too many regions. Found " + regionSpecList.size()
                    + " but max is " + NidUtil.MAX_NUM_REGIONS + ".");
        }
        if (regionSpecList.size() != numRegions) {
            throw new RuntimeException("Number of regions specified at brain construction ("
                    + numRegions + ") doesn't match number in spec (" + regionSpecList.size() + ").");
        }

        try {
            FileUtil.writeBrain(
                    einDirectory,
                    GlobalStaticHelper.objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem serializing brain into json: " + e.getMessage(), e);
        }

        for (byte rid = 0; rid < regionSpecList.size(); rid++) {
            RegionSpec regionSpec = regionSpecList.get(rid);
            int numClusters = regionSpec.getClusterSpecList().size();
            new Region(rid, numClusters)
                    .writeInitialDataFiles(
                            einDirectory,
                            regionSpec);
        }
    }
}
