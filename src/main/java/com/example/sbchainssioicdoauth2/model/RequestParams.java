package com.example.sbchainssioicdoauth2.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;

public class RequestParams extends LinkedMultiValueMap<String, String> {

    private static final long serialVersionUID = 1L;

    private Map<String, Object> ssiInfo = new HashMap<String, Object>();

    public Map<String, Object> getSsiInfo() {
        return ssiInfo;
    }

    public void setSsiInfo(final Map<String, Object> ssiInfo) {
        this.ssiInfo = ssiInfo;
    }

    public void stripEmpty() {
        final Map<String, Object> info = getSsiInfo();
        for (final Iterator<Map.Entry<String, Object>> entryIter = info.entrySet().iterator(); entryIter.hasNext();) {
            if (entryIter.next().getValue() == null) {
            entryIter.remove();
            }
        }

        //putAll(info);
    }

}