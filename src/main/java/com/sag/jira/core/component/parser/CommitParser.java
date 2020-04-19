package com.sag.jira.core.component.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sag.jira.core.obj.Commit;
import com.sag.jira.core.obj.Commit.Files;
import com.sag.jira.core.obj.Commit.Repo;
import com.sag.jira.core.obj.Review;
import com.sag.jira.core.obj.Review.Ireview;
import com.sag.jira.core.response.ItracReviewResponseBuilder.Response;
import com.sag.jira.util.JiraRestConfig;
import com.sun.jersey.api.client.ClientResponse;

public class CommitParser extends JiraParser {

	private List<Repo> allRepo = new ArrayList<>();
	private int totalNoOfLinesAdded = 0;
	private int totalNoOfLinesRemoved = 0;
	private int totalReviewCommentCounts = 0;
	private String issueGenerateId, itracId;
	private Set<String> uniquIssueGenaratedIds = new HashSet();
	private Response reviewMetrics;

	public CommitParser(ClientResponse response, String issueGenerateId, String itracId) throws JSONException {
		this.issueGenerateId = issueGenerateId;
		this.itracId = itracId;
		parseResponse(response);
		findAllCommitDetails();
	}

	private void findAllCommitDetails() {
		allRepo = new ArrayList<>();
		JSONArray details = jsonObject.optJSONArray(JiraRestConfig.Commit.DETAIL);
		if (isValidJsonArray(details)) {
			for (int i = 0; i < details.length(); i++) {
				JSONObject detail = details.optJSONObject(i);
				findRepositoriesDetails(detail);
			}
		}
	}

	private void findRepositoriesDetails(JSONObject detail) {
		JSONArray all_repositories = detail.optJSONArray(JiraRestConfig.Commit.REPOSITORIES);
		if (isValidJsonArray(all_repositories)) {
			for (int i = 0; i < all_repositories.length(); i++) {
				JSONObject repo = all_repositories.optJSONObject(i);
				findRepoDetails(repo);
			}
		}
	}

	private void findRepoDetails(JSONObject repositories) {
		if (isValidJsonObject(repositories)) {
			Commit commit = new Commit();
			Repo repo = commit.new Repo();
			repo = commit.new Repo();
			repo.setRepoName(repositories.optString(JiraRestConfig.Common.NAME));
			repo.setIreview_url(repositories.optString(JiraRestConfig.Commit.URL));
			fetchCommitDetails(repositories, repo);
			allRepo.add(repo);
		}
	}

	private void fetchCommitDetails(JSONObject repositories, Repo repo) {
		JSONArray commits = repositories.optJSONArray(JiraRestConfig.Commit.COMMITS);
		if (isValidJsonArray(commits)) {
			for (int i = 0; i < commits.length(); i++) {
				JSONObject commitObj = commits.optJSONObject(i);
				if (isValidJsonObject(commitObj)) {

					// we are interested to find the details if any commit happens
					int commitFileCount = commitObj.optInt(JiraRestConfig.Commit.FILECOUNT);
					if (commitFileCount > 0) {
						Commit commit = new Commit();
						commit.setCommitId(commitObj.optString(JiraRestConfig.Commit.ID));
						commit.setCommitIreviewUrl(commitObj.optString(JiraRestConfig.Commit.URL));
						commit.setCommitFileCounts(commitObj.optInt(JiraRestConfig.Commit.FILECOUNT));
						commit.setCommitMessage(commitObj.optString(JiraRestConfig.Commit.MESSAGE));
						updateAuthor(commitObj, commit);
						updateChangeFileDetails(commitObj, commit);
						updateCommitReviewDetails(commitObj, commit);
						repo.addCommit(commit);
					}
				}
			}
		}
	}

	private void updateAuthor(JSONObject commitObj, Commit commit) {
		JSONObject authorObj = commitObj.optJSONObject(JiraRestConfig.Commit.AUTHOR);
		commit.setCommitAuthor(authorObj.optString(JiraRestConfig.Common.NAME));
		commit.setCreateReviewUrl(commitObj.optString(JiraRestConfig.Commit.CREATE_REVIEW_URL));
	}

	private void updateChangeFileDetails(JSONObject commitObj, Commit commit) {
		JSONArray files = commitObj.optJSONArray(JiraRestConfig.Commit.FILES);
		if (isValidJsonArray(files)) {
			for (int i = 0; i < files.length(); i++) {
				JSONObject fileObj = files.optJSONObject(i);
				if (isValidJsonObject(fileObj)) {
					Files commitFile = commit.new Files();
					int linesAdded = fileObj.optInt(JiraRestConfig.File.LINESADDED);
					int linesRemoved = fileObj.optInt(JiraRestConfig.File.LINESREMOVED);
					totalNoOfLinesAdded += linesAdded;
					totalNoOfLinesRemoved += linesRemoved;
					commitFile = commit.new Files();
					commitFile.setPath(fileObj.optString(JiraRestConfig.File.PATH));
					commitFile.setUrl(fileObj.optString(JiraRestConfig.File.URL));
					commitFile.setChangeType(fileObj.optString(JiraRestConfig.File.CHANGETYPE));
					commitFile.setLinesAdded(linesAdded);
					commitFile.setLinesRemoved(linesRemoved);
					commit.addAllFiles(commitFile);
				}
			}
		}
	}

	private void updateCommitReviewDetails(JSONObject commitObj, Commit commit) {
		if (isValidJsonObject(commitObj)) {
			JSONObject ireviewObj = commitObj.optJSONObject(JiraRestConfig.Commit.REVIEWS);
			if (isValidJsonObject(ireviewObj)) {
				int totalCount = ireviewObj.optInt(JiraRestConfig.Review.TOTAL_COUNT);

				// we are interested only if any review done
				if (totalCount > 0) {
					Review review = new Review();
					Ireview ireviewDetails = review.new Ireview();
					ireviewDetails.setTotalCount(totalCount);
					String irevieUrl = ireviewObj.optString(JiraRestConfig.Review.URL);
					ireviewDetails.setUrl(irevieUrl);
					JSONArray reviews = ireviewObj.optJSONArray(JiraRestConfig.Review.REVIEWS);
					if (isValidJsonArray(reviews)) {
						for (int i = 0; i < reviews.length(); i++) {
							JSONObject reviewObj = reviews.optJSONObject(i);
							if (isValidJsonObject(reviewObj)) {
								review = new Review();
								review.setId(reviewObj.optString(JiraRestConfig.Review.ID));
								review.setUrl(reviewObj.optString(JiraRestConfig.Review.URL));
								review.setState(reviewObj.optString(JiraRestConfig.Review.STATE));
								review.setTitle(reviewObj.optString(JiraRestConfig.Review.TITLE));
								review.setIssueGenaratedId(issueGenerateId);
								updateReviewCommentCounts(issueGenerateId, review);
								ireviewDetails.addReview(review);
							}
						}
					}
					commit.addIreview(ireviewDetails);
				}
			}
		}
	}

	/**
	 * Method to avoid the addition of the same review comment history more then
	 * once; as more then one commit can relate to one review story
	 * 
	 * @param issueGenerateId
	 * @param review
	 */
	private void updateReviewCommentCounts(String issueGenerateId, Review review) {
		if (uniquIssueGenaratedIds.contains(issueGenerateId))
			return;
		totalReviewCommentCounts = review.getReviewCommentsCount(itracId);
		uniquIssueGenaratedIds.add(issueGenerateId);
		reviewMetrics = review.getReviewMetrics(itracId);
	}

	public ClientResponse getResponse() {
		return response;
	}

	private Commit getRecentCommit() {
		if (!allRepo.isEmpty()) {
			List<Commit> allCommitsForOneRepo = allRepo.get(0).getAllCommits();

			if (allCommitsForOneRepo.size() >= 1) {
				Collections.sort(allCommitsForOneRepo, new CommitIdComparator());
				return allCommitsForOneRepo.get(allCommitsForOneRepo.size() - 1);
			}
		}
		return null;
	}

	public boolean isCommitDone() {
		Commit recentCommit = getRecentCommit();
		if (recentCommit == null)
			return false;
		return true;

	}

	public boolean isReviewDoneForRecentCommit() {
		Commit recentCommit = getRecentCommit();
		if (recentCommit != null) {
			List<Ireview> ireviews = recentCommit.getAllIreviews();
			for (Ireview ireview : ireviews) {
				if (ireview.isReviewDone())
					return true;
			}
		}
		return false;
	}

	public String getIReviewUrlForRecentCommit() {
		Commit recentCommit = getRecentCommit();
		if (recentCommit != null) {
			return recentCommit.getCreateReviewUrl();
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getChangelogUrlForRecentCommit() {
		Commit recentCommit = getRecentCommit();
		if (recentCommit != null) {
			return recentCommit.getCommitIreviewUrl();
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getAuthorForRecentCommit() {
		Commit recentCommit = getRecentCommit();
		if (recentCommit != null) {
			return recentCommit.getCommitAuthor();
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public String getRecentCommitId() {
		Commit recentCommit = getRecentCommit();
		if (recentCommit != null) {
			return recentCommit.getCommitId();
		}
		return JiraRestConfig.Common.EMPTY;
	}

	public int getTotalNoOfLinesAdded() {
		return totalNoOfLinesAdded;
	}

	public int getTotalNoOfLinesRemoved() {
		return totalNoOfLinesRemoved;
	}

	public int getTotalReviewComments() {
		return totalReviewCommentCounts;
	}

	public Response getReviewMetrics() {
		return reviewMetrics;
	}

}

class CommitIdComparator implements Comparator<Commit> {

	@Override
	public int compare(Commit o1, Commit o2) {
		return o1.getCommitId().compareTo(o2.getCommitId());
	}

}
