package org.sheinbergon.aac.jna.structure;

import com.sun.jna.Structure;
import org.sheinbergon.aac.jna.util.JNAUtil;

import java.util.List;

public class LibInfo extends Structure {

    private final static List<String> FIELD_ORDER = JNAUtil.structureFieldOrder(LibInfo.class);
    
    public String title;
    public String build_date;
    public String build_time;
    public int module_id;
    public int version;
    public int flags;
    public byte[] versionStr = new byte[32];

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }
}
