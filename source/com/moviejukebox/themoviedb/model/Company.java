/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.themoviedb.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Company information
 *
 * @author Stuart
 */
public class Company implements IModelAttribute
{
    // Logger

    private static final Logger LOGGER = Logger.getLogger(Company.class);
    private static final String DEFAULT_STRING = "";

    private final String ATTR_ID = "id";
    private final String ATTR_NAME = "name";
    private final String ATTR_DESCRIPTION = "description";
    private final String ATTR_HEADQUARTERS = "headquarters";
    private final String ATTR_HOMEPAGE = "homepage";
    private final String ATTR_LOGO_PATH = "logo_path";
    private final String ATTR_PARENT_COMPANY = "parent_company";

    // Properties
    @JsonProperty(ATTR_ID)
    private int companyId = 0;
    @JsonProperty(ATTR_NAME)
    private String name = DEFAULT_STRING;
    @JsonProperty(ATTR_DESCRIPTION)
    private String description = DEFAULT_STRING;
    @JsonProperty(ATTR_HEADQUARTERS)
    private String headquarters = DEFAULT_STRING;
    @JsonProperty(ATTR_HOMEPAGE)
    private String homepage = DEFAULT_STRING;
    @JsonProperty(ATTR_LOGO_PATH)
    private String logoPath = DEFAULT_STRING;
    @JsonProperty(ATTR_PARENT_COMPANY)
    private String parentCompany = DEFAULT_STRING;

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public int getCompanyId() {
        return companyId;
    }

    public String getDescription() {
        return description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }

    public String getParentCompany() {
        return parentCompany;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }
    //</editor-fold>

    /**
     * Handle unknown properties and print a message
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");
        LOGGER.warn(sb.toString());
    }

    @Override
    public String toString() {
        return "Company{" + "companyId=" + companyId + ", name=" + name + ", description=" + description + ", headquarters=" + headquarters + ", homepage=" + homepage + ", logoPath=" + logoPath + ", parentCompany=" + parentCompany + '}';
    }

    @Override
    public Map<String, Object> getValueMap()
    {
        Map<String, Object> map = new HashMap();
        map.put(ATTR_ID, companyId);
        map.put(ATTR_NAME, name);
        map.put(ATTR_DESCRIPTION, description);
        map.put(ATTR_HEADQUARTERS, headquarters);
        map.put(ATTR_HOMEPAGE, homepage);
        map.put(ATTR_LOGO_PATH, logoPath);
        map.put(ATTR_PARENT_COMPANY, parentCompany);
        return map;
    }
}
