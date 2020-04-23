package com.sag.jira.core.obj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sag.jira.util.JiraRestConfig;

public class Commit {

	private String id = JiraRestConfig.Common.EMPTY;
	private String message = JiraRestConfig.Common.EMPTY;
	private String author = JiraRestConfig.Common.EMPTY;
	private int commitFileCount = 0;
	private String commitDate = JiraRestConfig.Common.EMPTY;
	private String commitIreviewUrl = JiraRestConfig.Common.EMPTY;
	private String createReviewUrl = JiraRestConfig.Common.EMPTY;
	private static Set<String> commitIds = new HashSet<>();

	private List<Review.Ireview> allIreviews = new ArrayList();
	private List<Files> allFiles = new ArrayList<>();

	public String getCommitId() {
		return id;
	}

	public void setCommitId(String commitId) {
		commitIds.add(commitId);
		this.id = commitId;
	}

	public String getCommitMessage() {
		return message;
	}

	public void setCommitMessage(String commitMessage) {
		this.message = commitMessage;
	}

	public String getCommitAuthor() {
		return author;
	}

	public void setCommitAuthor(String commitAuthor) {
		this.author = commitAuthor;
	}

	public int getCommitFileCounts() {
		return commitFileCount;
	}

	public String getCreateReviewUrl() {
		return createReviewUrl;
	}

	public void setCreateReviewUrl(String createReviewUrl) {
		this.createReviewUrl = createReviewUrl;
	}

	public void setCommitFileCounts(int commitFiles) {
		this.commitFileCount = commitFiles;
	}

	public String getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}

	public String getCommitIreviewUrl() {
		return commitIreviewUrl;
	}

	public void setCommitIreviewUrl(String commitIreviewUrl) {
		this.commitIreviewUrl = commitIreviewUrl;
	}

	public List<Review.Ireview> getAllIreviews() {
		return allIreviews;
	}

	public void addIreview(Review.Ireview ireview) {
		this.allIreviews.add(ireview);
	}

	public void addAllFiles(Files file) {
		this.allFiles.add(file);
	}

	public class Repo {

		private String repoName = "";
		private String ireview_url = "";

		List<Commit> allCommits = new ArrayList();

		public String getRepoName() {
			return repoName;
		}

		public void setRepoName(String repoName) {
			this.repoName = repoName;
		}

		public String getIreview_url() {
			return ireview_url;
		}

		public void setIreview_url(String ireview_url) {
			this.ireview_url = ireview_url;
		}

		public List<Commit> getAllCommits() {
			return allCommits;
		}

		public void addCommit(Commit commit) {
			this.allCommits.add(commit);
		}
	}

	public class Files {
		private String path;
		private String url;
		private String changeType;
		private int linesAdded;
		private int linesRemoved;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getChangeType() {
			return changeType;
		}

		public void setChangeType(String changeType) {
			this.changeType = changeType;
		}

		public int getLinesAdded() {
			return linesAdded;
		}

		public void setLinesAdded(int linesAdded) {
			this.linesAdded = linesAdded;
		}

		public int getLinesRemoved() {
			return linesRemoved;
		}

		public void setLinesRemoved(int linesRemoved) {
			this.linesRemoved = linesRemoved;
		}

	}

	public boolean isAlreadyCaptured(String commitId) {
		return commitIds.contains(commitId);
	}

}
