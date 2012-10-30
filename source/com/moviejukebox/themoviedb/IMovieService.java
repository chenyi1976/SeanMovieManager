package com.moviejukebox.themoviedb;

import java.util.List;
import java.util.Map;

import com.moviejukebox.themoviedb.model.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public interface IMovieService
{
    /**
     * Search Movies This is a good starting point to start finding movies on TMDb.
     *
     * The idea is to be a quick and light method so you can iterate through movies quickly.
     *
     * http://help.themoviedb.org/kb/api/search-movies
     *
     * TODO: Make the allResults work
     *
     * @param movieName
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<MovieDb> searchMovie(String movieName, String language, boolean allResults) throws MovieDbException;

    /**
     * This method is used to retrieve all of the basic movie information.
     *
     * It will return the single highest rated poster and backdrop.
     *
     * @param movieId
     * @param language
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    MovieDb getMovieInfo(int movieId, String language) throws MovieDbException;

    /**
     * This method is used to retrieve all of the alternative titles we have for a particular movie.
     *
     * @param movieId
     * @param country
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<AlternativeTitle> getMovieAlternativeTitles(int movieId, String country) throws MovieDbException;

    /**
     * This method is used to retrieve all of the movie cast information.
     *
     * TODO: Add a function to enrich the data with the people methods
     *
     * @param movieId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Person> getMovieCasts(int movieId) throws MovieDbException;

    /**
     * This method should be used when you’re wanting to retrieve all of the images for a particular movie.
     *
     * @param movieId
     * @param language
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Artwork> getMovieImages(int movieId, String language) throws MovieDbException;

    /**
     * This method is used to retrieve all of the keywords that have been added to a particular movie.
     *
     * Currently, only English keywords exist.
     *
     * @param movieId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Keyword> getMovieKeywords(int movieId) throws MovieDbException;

    /**
     * This method is used to retrieve all of the release and certification data we have for a specific movie.
     *
     * @param movieId
     * @param language
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<ReleaseInfo> getMovieReleaseInfo(int movieId, String language) throws MovieDbException;

    /**
     * This method is used to retrieve all of the trailers for a particular movie.
     *
     * Supported sites are YouTube and QuickTime.
     *
     * @param movieId
     * @param language
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Trailer> getMovieTrailers(int movieId, String language) throws MovieDbException;

    /**
     * This method is used to retrieve a list of the available translations for a specific movie.
     *
     * @param movieId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Translation> getMovieTranslations(int movieId) throws MovieDbException;

    /**
     * This method is used to retrieve all of the basic information about a movie collection.
     *
     * You can get the ID needed for this method by making a getMovieInfo request for the belongs_to_collection.
     *
     * @param movieId
     * @param language
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    CollectionInfo getCollectionInfo(int movieId, String language) throws MovieDbException;

    /**
     * Get the configuration information
     *
     * @return
     */
    TmdbConfiguration getConfiguration();

    /**
     * This is a good starting point to start finding people on TMDb.
     *
     * The idea is to be a quick and light method so you can iterate through people quickly.
     *
     * TODO: Fix allResults
     *
     * @param personName
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Person> searchPeople(String personName, boolean allResults) throws MovieDbException;

    /**
     * This method is used to retrieve all of the basic person information.
     *
     * It will return the single highest rated profile image.
     *
     * @param personId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    Person getPersonInfo(int personId) throws MovieDbException;

    /**
     * This method is used to retrieve all of the cast & crew information for the person.
     *
     * It will return the single highest rated poster for each movie record.
     *
     * @param personId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<PersonCredit> getPersonCredits(int personId) throws MovieDbException;

    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Artwork> getPersonImages(int personId) throws MovieDbException;

    /**
     * This method is used to retrieve the newest movie that was added to TMDb.
     *
     * @return
     */
    MovieDb getLatestMovie() throws MovieDbException;

    /**
     * This method is used to retrieve the movies currently in theatres.
     *
     * This is a curated list that will normally contain 100 movies. The default response will return 20 movies.
     *
     * TODO: Implement more than 20 movies
     *
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<MovieDb> getNowPlayingMovies(String language, boolean allResults) throws MovieDbException;

    /**
     * This method is used to retrieve the daily movie popularity list.
     *
     * This list is updated daily. The default response will return 20 movies.
     *
     * TODO: Implement more than 20 movies
     *
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<MovieDb> getPopularMovieList(String language, boolean allResults) throws MovieDbException;

    /**
     * This method is used to retrieve the top rated movies that have over 10 votes on TMDb.
     *
     * The default response will return 20 movies.
     *
     * TODO: Implement more than 20 movies
     *
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<MovieDb> getTopRatedMovies(String language, boolean allResults) throws MovieDbException;

    /**
     * This method is used to retrieve the basic information about a production company on TMDb.
     *
     * @param companyId
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    Company getCompanyInfo(int companyId) throws MovieDbException;

    /**
     * This method is used to retrieve the movies associated with a company.
     *
     * These movies are returned in order of most recently released to oldest. The default response will return 20
     * movies per page.
     *
     * TODO: Implement more than 20 movies
     *
     * @param companyId
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<MovieDb> getCompanyMovies(int companyId, String language, boolean allResults) throws MovieDbException;

    /**
     * Search Companies.
     *
     * You can use this method to search for production companies that are part of TMDb. The company IDs will map to
     * those returned on movie calls.
     *
     * http://help.themoviedb.org/kb/api/search-companies
     *
     * TODO: Make the allResults work
     *
     * @param companyName
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<Company> searchCompanies(String companyName, String language, boolean allResults) throws MovieDbException;

    /**
     * The similar movies method will let you retrieve the similar movies for a particular movie.
     *
     * This data is created dynamically but with the help of users votes on TMDb.
     *
     * The data is much better with movies that have more keywords
     *
     * @param movieId
     * @param language
     * @param allResults
     * @return
     * @throws com.moviejukebox.themoviedb.MovieDbException
     */
    List<MovieDb> getSimilarMovies(int movieId, String language, boolean allResults) throws MovieDbException;

    /**
     * You can use this method to retrieve the list of genres used on TMDb.
     *
     * These IDs will correspond to those found in movie calls.
     *
     * @param language
     * @return
     */
    List<Genre> getGenreList(String language) throws MovieDbException;

    /**
     * Get a list of movies per genre.
     *
     * It is important to understand that only movies with more than 10 votes get listed.
     *
     * This prevents movies from 1 10/10 rating from being listed first and for the first 5 pages.
     *
     * @param genreId
     * @param language
     * @param allResults
     * @return
     */
    List<MovieDb> getGenreMovies(int genreId, String language, boolean allResults) throws MovieDbException;

    Map<String, Map<String, Class>> getNodeTypeAttributeMap();

    List<Class> getNodeTypeClass();
}
