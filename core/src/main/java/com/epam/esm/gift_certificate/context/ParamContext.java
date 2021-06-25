package com.epam.esm.gift_certificate.context;

import java.util.List;
import java.util.Map;

public class ParamContext {
    private Map<String, String> searchMap;

    private List<String> sortTypes;

    public ParamContext(Map<String, String> searchMap, List<String> sortTypes) {
        this.searchMap = searchMap;
        this.sortTypes = sortTypes;
    }

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }

    public List<String> getSortTypes() {
        return sortTypes;
    }

    public void setSortTypes(List<String> sortTypes) {
        this.sortTypes = sortTypes;
    }
}
