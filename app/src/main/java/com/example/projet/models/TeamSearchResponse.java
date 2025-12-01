package com.example.projet.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class TeamSearchResponse {
    @SerializedName("get")
    private String get;
    
    @SerializedName("parameters")
    private Map<String, Object> parameters;
    
    @SerializedName("errors")
    private Map<String, String> errors;
    
    @SerializedName("results")
    private int results;
    
    @SerializedName("paging")
    private Map<String, Object> paging;
    
    @SerializedName("response")
    private List<TeamDetail> response;

    public TeamSearchResponse() {}

    public String getGet() { return get; }
    public void setGet(String get) { this.get = get; }

    public Map<String, Object> getParameters() { return parameters; }
    public void setParameters(Map<String, Object> parameters) { this.parameters = parameters; }

    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> errors) { this.errors = errors; }

    public int getResults() { return results; }
    public void setResults(int results) { this.results = results; }

    public Map<String, Object> getPaging() { return paging; }
    public void setPaging(Map<String, Object> paging) { this.paging = paging; }

    public List<TeamDetail> getResponse() { return response; }
    public void setResponse(List<TeamDetail> response) { this.response = response; }
}
