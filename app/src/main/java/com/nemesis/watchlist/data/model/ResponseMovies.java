package com.nemesis.watchlist.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ResponseMovies implements Serializable {

	@SerializedName("page")
	private int page;

	@SerializedName("total_results")
	private int totalResults;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsMovies> results;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsMovies> results){
		this.results = results;
	}

	public List<ResultsMovies> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return
			"ResponseMovies{" +
			"page = '" + page + '\'' +
			",total_results = '" + totalResults + '\'' +
			",total_pages = '" + totalPages + '\'' +
			",results = '" + results + '\'' +
			"}";
		}
}