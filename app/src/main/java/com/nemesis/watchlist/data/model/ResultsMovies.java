package com.nemesis.watchlist.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsMovies implements Parcelable {

	@SerializedName("popularity")
	private String popularity;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("video")
	private boolean video;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("title")
	private String title;

	@SerializedName("vote_average")
	private String voteAverage;

	@SerializedName("overview")
	private String overview;

	@SerializedName("release_date")
	private String releaseDate;


	protected ResultsMovies(Parcel in) {
		popularity = in.readString();
		voteCount = in.readInt();
		video = in.readByte() != 0;
		posterPath = in.readString();
		id = in.readInt();
		adult = in.readByte() != 0;
		backdropPath = in.readString();
		originalLanguage = in.readString();
		originalTitle = in.readString();
		title = in.readString();
		voteAverage = in.readString();
		overview = in.readString();
		releaseDate = in.readString();
	}

	public static final Creator<ResultsMovies> CREATOR = new Creator<ResultsMovies>() {
		@Override
		public ResultsMovies createFromParcel(Parcel in) {
			return new ResultsMovies(in);
		}

		@Override
		public ResultsMovies[] newArray(int size) {
			return new ResultsMovies[size];
		}
	};

	public void setPopularity(String popularity){
		this.popularity = popularity;
	}

	public String getPopularity(){
		return popularity;
	}

	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}

	public int getVoteCount(){
		return voteCount;
	}

	public void setVideo(boolean video){
		this.video = video;
	}

	public boolean isVideo(){
		return video;
	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAdult(boolean adult){
		this.adult = adult;
	}

	public boolean isAdult(){
		return adult;
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

	public void setOriginalTitle(String originalTitle){
		this.originalTitle = originalTitle;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public void setGenreIds(List<Integer> genreIds){
		this.genreIds = genreIds;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
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

	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	@Override
 	public String toString(){
		return
			"ResultsMovies{" +
			"popularity = '" + popularity + '\'' +
			",vote_count = '" + voteCount + '\'' +
			",video = '" + video + '\'' +
			",poster_path = '" + posterPath + '\'' +
			",id = '" + id + '\'' +
			",adult = '" + adult + '\'' +
			",backdrop_path = '" + backdropPath + '\'' +
			",original_language = '" + originalLanguage + '\'' +
			",original_title = '" + originalTitle + '\'' +
			",genre_ids = '" + genreIds + '\'' +
			",title = '" + title + '\'' +
			",vote_average = '" + voteAverage + '\'' +
			",overview = '" + overview + '\'' +
			",release_date = '" + releaseDate + '\'' +
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(popularity);
		parcel.writeInt(voteCount);
		parcel.writeByte((byte) (video ? 1 : 0));
		parcel.writeString(posterPath);
		parcel.writeInt(id);
		parcel.writeByte((byte) (adult ? 1 : 0));
		parcel.writeString(backdropPath);
		parcel.writeString(originalLanguage);
		parcel.writeString(originalTitle);
		parcel.writeString(title);
		parcel.writeString(voteAverage);
		parcel.writeString(overview);
		parcel.writeString(releaseDate);
	}
}