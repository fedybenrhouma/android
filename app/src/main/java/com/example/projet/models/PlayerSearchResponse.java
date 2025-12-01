 package com.example.projet.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class PlayerSearchResponse {
    @SerializedName("get")
    private String get;
    
    @SerializedName("parameters")
    private Parameters parameters;
    
    @SerializedName("errors")
    private Map<String, String> errors;
    
    @SerializedName("results")
    private int results;
    
    @SerializedName("paging")
    private Paging paging;
    
    @SerializedName("response")
    private List<Player> response;

    public PlayerSearchResponse() {}

    public String getGet() { return get; }
    public void setGet(String get) { this.get = get; }

    public Parameters getParameters() { return parameters; }
    public void setParameters(Parameters parameters) { this.parameters = parameters; }

    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> errors) { this.errors = errors; }

    public int getResults() { return results; }
    public void setResults(int results) { this.results = results; }

    public Paging getPaging() { return paging; }
    public void setPaging(Paging paging) { this.paging = paging; }

    public List<Player> getResponse() { return response; }
    public void setResponse(List<Player> response) { this.response = response; }
}

class Parameters {
    @SerializedName("search")
    private String search;
    
    @SerializedName("season")
    private int season;

    public Parameters() {}

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }

    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }
}

class Paging {
    @SerializedName("current")
    private int current;
    
    @SerializedName("total")
    private int total;

    public Paging() {}

    public int getCurrent() { return current; }
    public void setCurrent(int current) { this.current = current; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
