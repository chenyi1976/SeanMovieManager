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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Movie Bean
 *
 * @author stuart.boston
 */
public class MovieDb implements IModelAttribute
{

    /*
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(MovieDb.class);

    public final static String ATTR_BACKDROP_PATH = "backdrop_path";
    public final static String ATTR_ID = "id";
    public final static String ATTR_ORIGINAL_TITLE = "original_title";
    public final static String ATTR_POPULARITY = "popularity";
    public final static String ATTR_POSTER_PATH = "poster_path";
    public final static String ATTR_RELEASE_DATE = "release_date";
    public final static String ATTR_TITLE = "title";
    public final static String ATTR_ADULT = "adult";
    public final static String ATTR_BELONGS_TO_COLLECTION = "belongs_to_collection";
    public final static String ATTR_BUDGET = "budget";
    public final static String ATTR_GENRES = "genres";
    public final static String ATTR_HOMEPAGE = "homepage";
    public final static String ATTR_IMDB_ID = "imdb_id";
    public final static String ATTR_OVERVIEW = "overview";
    public final static String ATTR_PRODUCTION_COMPANIES = "production_companies";
    public final static String ATTR_PRODUCTION_COUNTRIES = "production_countries";
    public final static String ATTR_REVENUE = "revenue";
    public final static String ATTR_RUNTIME = "runtime";
    public final static String ATTR_SPOKEN_LANGUAGE = "spoken_languages";
    public final static String ATTR_TAGLINE = "tagline";
    public final static String ATTR_VOTE_AVERAGE = "vote_average";
    public final static String ATTR_VOTE_COUNT = "vote_count";


    /*
    * Properties
    */
    @JsonProperty(ATTR_BACKDROP_PATH)
    @MovieImage
    private String backdropPath;
    @JsonProperty(ATTR_ID)
    private int id;
    @JsonProperty(ATTR_ORIGINAL_TITLE)
    private String originalTitle;
    @JsonProperty(ATTR_POPULARITY)
    private float popularity;
    @JsonProperty(ATTR_POSTER_PATH)
    @MovieImage
    private String posterPath;
    @JsonProperty(ATTR_RELEASE_DATE)
    private String releaseDate;
    @JsonProperty(ATTR_TITLE)
    private String title;
    @JsonProperty(ATTR_ADULT)
    private boolean adult;
    @JsonProperty(ATTR_BELONGS_TO_COLLECTION)
    private Collection belongsToCollection;
    @JsonProperty(ATTR_BUDGET)
    private long budget;
    @JsonProperty(ATTR_GENRES)
    private List<Genre> genres;
    @JsonProperty(ATTR_HOMEPAGE)
    private String homepage;
    @JsonProperty(ATTR_IMDB_ID)
    private String imdbID;
    @JsonProperty(ATTR_OVERVIEW)
    private String overview;
    @JsonProperty(ATTR_PRODUCTION_COMPANIES)
    private List<ProductionCompany> productionCompanies;
    @JsonProperty(ATTR_PRODUCTION_COUNTRIES)
    private List<ProductionCountry> productionCountries;
    @JsonProperty(ATTR_REVENUE)
    private long revenue;
    @JsonProperty(ATTR_RUNTIME)
    private int runtime;
    @JsonProperty(ATTR_SPOKEN_LANGUAGE)
    private List<Language> spokenLanguages;
    @JsonProperty(ATTR_TAGLINE)
    private String tagline;
    @JsonProperty(ATTR_VOTE_AVERAGE)
    private float voteAverage;
    @JsonProperty(ATTR_VOTE_COUNT)
    private int voteCount;

    private static final Map<String,Class> attributeClassMap = new HashMap();

    static
    {
        attributeClassMap.put(ATTR_BACKDROP_PATH, String.class);
        attributeClassMap.put(ATTR_ID, Integer.class);
        attributeClassMap.put(ATTR_ORIGINAL_TITLE, String.class);
        attributeClassMap.put(ATTR_POPULARITY, Float.class);
        attributeClassMap.put(ATTR_POSTER_PATH, String.class);
        attributeClassMap.put(ATTR_RELEASE_DATE, String.class);
        attributeClassMap.put(ATTR_TITLE, String.class);
        attributeClassMap.put(ATTR_ADULT, Boolean.class);
        attributeClassMap.put(ATTR_BELONGS_TO_COLLECTION, Collection.class);
        attributeClassMap.put(ATTR_BUDGET, Long.class);
        attributeClassMap.put(ATTR_GENRES, List.class);
        attributeClassMap.put(ATTR_HOMEPAGE, String.class);
        attributeClassMap.put(ATTR_IMDB_ID, String.class);
        attributeClassMap.put(ATTR_OVERVIEW, String.class);
        attributeClassMap.put(ATTR_PRODUCTION_COMPANIES, List.class);
        attributeClassMap.put(ATTR_PRODUCTION_COUNTRIES, List.class);
        attributeClassMap.put(ATTR_REVENUE, Long.class);
        attributeClassMap.put(ATTR_RUNTIME, Integer.class);
        attributeClassMap.put(ATTR_SPOKEN_LANGUAGE, List.class);
        attributeClassMap.put(ATTR_TAGLINE, String.class);
        attributeClassMap.put(ATTR_VOTE_AVERAGE, Float.class);
        attributeClassMap.put(ATTR_VOTE_COUNT, Integer.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdult() {
        return adult;
    }

    public Collection getBelongsToCollection() {
        return belongsToCollection;
    }

    public long getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getOverview() {
        return overview;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getTagline() {
        return tagline;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBelongsToCollection(Collection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSpokenLanguages(List<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    // </editor-fold>

    public static Map<String, Class> getValueClass()
    {
        return attributeClassMap;
    }

    public Map<String, Object> getValueMap()
    {
        Map<String, Object> map = new HashMap();
        map.put(ATTR_BACKDROP_PATH, backdropPath);
        map.put(ATTR_ID, id);
        map.put(ATTR_ORIGINAL_TITLE, originalTitle);
        map.put(ATTR_POPULARITY, popularity);
        map.put(ATTR_POSTER_PATH, posterPath);
        map.put(ATTR_RELEASE_DATE, releaseDate);
        map.put(ATTR_TITLE, title);
        map.put(ATTR_ADULT, adult);
        map.put(ATTR_BELONGS_TO_COLLECTION, belongsToCollection);
        map.put(ATTR_BUDGET, budget);
        map.put(ATTR_GENRES, genres);
        map.put(ATTR_HOMEPAGE, homepage);
        map.put(ATTR_IMDB_ID, imdbID);
        map.put(ATTR_OVERVIEW, overview);
        map.put(ATTR_PRODUCTION_COMPANIES, productionCompanies);
        map.put(ATTR_PRODUCTION_COUNTRIES, productionCountries);
        map.put(ATTR_REVENUE, revenue);
        map.put(ATTR_RUNTIME, runtime);
        map.put(ATTR_SPOKEN_LANGUAGE, spokenLanguages);
        map.put(ATTR_TAGLINE, tagline);
        map.put(ATTR_VOTE_AVERAGE, voteAverage);
        map.put(ATTR_VOTE_COUNT, voteCount);
        return map;
    }

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

    //<editor-fold defaultstate="collapsed" desc="Equals and HashCode">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovieDb other = (MovieDb) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.imdbID == null) ? (other.imdbID != null) : !this.imdbID.equals(other.imdbID)) {
            return false;
        }
        if (this.runtime != other.runtime) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.imdbID != null ? this.imdbID.hashCode() : 0);
        hash = 89 * hash + this.runtime;
        return hash;
    }
    //</editor-fold>

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[MovieDB=");
        sb.append("[backdropPath=").append(backdropPath);
        sb.append("],[id=").append(id);
        sb.append("],[originalTitle=").append(originalTitle);
        sb.append("],[popularity=").append(popularity);
        sb.append("],[posterPath=").append(posterPath);
        sb.append("],[releaseDate=").append(releaseDate);
        sb.append("],[title=").append(title);
        sb.append("],[adult=").append(adult);
        sb.append("],[belongsToCollection=").append(belongsToCollection);
        sb.append("],[budget=").append(budget);
        sb.append("],[genres=").append(genres);
        sb.append("],[homepage=").append(homepage);
        sb.append("],[imdbID=").append(imdbID);
        sb.append("],[overview=").append(overview);
        sb.append("],[productionCompanies=").append(productionCompanies);
        sb.append("],[productionCountries=").append(productionCountries);
        sb.append("],[revenue=").append(revenue);
        sb.append("],[runtime=").append(runtime);
        sb.append("],[spokenLanguages=").append(spokenLanguages);
        sb.append("],[tagline=").append(tagline);
        sb.append("],[voteAverage=").append(voteAverage);
        sb.append("],[voteCount=").append(voteCount);
        sb.append("]]");
        return sb.toString();
    }
}
