package com.nemesis.watchlist.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResultsSeries implements Parcelable {

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("name")
	private String name;

	@SerializedName("popularity")
	private String popularity;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("id")
	private int id;

	@SerializedName("vote_average")
	private String voteAverage;

	@SerializedName("overview")
	private String overview;

	@SerializedName("poster_path")
	private String posterPath;

	protected ResultsSeries(Parcel in) {
		originalName = in.readString();
		name = in.readString();
		popularity = in.readString();
		originCountry = in.createStringArrayList();
		voteCount = in.readInt();
		firstAirDate = in.readString();
		backdropPath = in.readString();
		originalLanguage = in.readString();
		id = in.readInt();
		voteAverage = in.readString();
		overview = in.readString();
		posterPath = in.readString();
	}

	public static final Creator<ResultsSeries> CREATOR = new Creator<ResultsSeries>() {
		@Override
		public ResultsSeries createFromParcel(Parcel in) {
			return new ResultsSeries(in);
		}

		@Override
		public ResultsSeries[] newArray(int size) {
			return new ResultsSeries[size];
		}
	};

	public void setOriginalName(String originalName){
		this.originalName = originalName;
	}

	public String getOriginalName(){
		return originalName;
	}

	public void setGenreIds(List<Integer> genreIds){
		this.genreIds = genreIds;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPopularity(String popularity){
		this.popularity = popularity;
	}

	public String getPopularity(){
		return popularity;
	}

	public void setOriginCountry(List<String> originCountry){
		this.originCountry = originCountry;
	}

	public List<String> getOriginCountry(){
		return originCountry;
	}

	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}

	public int getVoteCount(){
		return voteCount;
	}

	public void setFirstAirDate(String firstAirDate){
		this.firstAirDate = firstAirDate;
	}

	public String getFirstAirDate(){
		return firstAirDate;
	}

	public void setBackdropPath(String backdropPath){
		this.backdropPath = backdropPath;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public void setOriginalLanguage(String originalLanguage){
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setVoteAverage(String voteAverage){
		this.voteAverage = voteAverage;
	}

	public String getVoteAverage(){
		return voteAverage;
	}

	public void setOverview(String overview){
		this.overview = overview;
	}

	public String getOverview(){
		return overview;
	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

	@Override
 	public String toString(){
		return
			"ResultsSeries{" +
			"original_name = '" + originalName + '\'' +
			",genre_ids = '" + genreIds + '\'' +
			",name = '" + name + '\'' +
			",popularity = '" + popularity + '\'' +
			",origin_country = '" + originCountry + '\'' +
			",vote_count = '" + voteCount + '\'' +
			",first_air_date = '" + firstAirDate + '\'' +
			",backdrop_path = '" + backdropPath + '\'' +
			",original_language = '" + originalLanguage + '\'' +
			",id = '" + id + '\'' +
			",vote_average = '" + voteAverage + '\'' +
			",overview = '" + overview + '\'' +
			",poster_path = '" + posterPath + '\'' +
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(originalName);
		parcel.writeString(name);
		parcel.writeString(popularity);
		parcel.writeStringList(originCountry);
		parcel.writeInt(voteCount);
		parcel.writeString(firstAirDate);
		parcel.writeString(backdropPath);
		parcel.writeString(originalLanguage);
		parcel.writeInt(id);
		parcel.writeString(voteAverage);
		parcel.writeString(overview);
		parcel.writeString(posterPath);
	}
}