package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.generator.RegionSpec;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.ArrayList;
import java.util.List;

public class Brain extends AbstractComponent {

    List<Region> regions;

    public Brain(ConnectomeGenSpec spec) {

        List<RegionSpec> regionSpecList = spec.getRegionSpecList();
        if (regionSpecList.size() > NidUtil.MAX_NUM_REGIONS) {
            throw new RuntimeException("Too many regions. Found " + regionSpecList.size()
                    + " but max is " + NidUtil.MAX_NUM_REGIONS + ".");
        }

        // todo: Obviously, there are WAY too many entities being created in all these nested loops.
        //  This is just a temporary step in development.
        //  Need to write these entities to disk, remove references to the objects, and then garbage-collect.

        regions = new ArrayList<>(regionSpecList.size());
        for (byte rid = 0; rid < regionSpecList.size(); rid++) {
            regions.add(
                    new Region(
                            rid,
                            regionSpecList.get(rid)));
        }
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of regions: ").append(regions.size());
    }
}
