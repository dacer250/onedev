package io.onedev.server.search.entity.pullrequest;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import io.onedev.server.model.Project;
import io.onedev.server.model.PullRequest;
import io.onedev.server.model.User;
import io.onedev.server.model.support.pullrequest.MergeStrategy;
import io.onedev.server.search.entity.QueryBuildContext;
import io.onedev.server.util.PullRequestConstants;

public class MergeStrategyCriteria extends PullRequestCriteria {

	private static final long serialVersionUID = 1L;

	private MergeStrategy value;
	
	public MergeStrategyCriteria(MergeStrategy value) {
		this.value = value;
	}

	@Override
	public Predicate getPredicate(Project project, QueryBuildContext<PullRequest> context, User user) {
		Path<?> attribute = context.getRoot().get(PullRequestConstants.ATTR_MERGE_STRATEGY);
		return context.getBuilder().equal(attribute, value);
	}

	@Override
	public boolean matches(PullRequest request, User user) {
		return request.getMergeStrategy() == value;
	}

	@Override
	public boolean needsLogin() {
		return false;
	}

	@Override
	public String toString() {
		return PullRequestQuery.quote(PullRequestConstants.FIELD_MERGE_STRATEGY) + " " + PullRequestQuery.getRuleName(PullRequestQueryLexer.Is) + " " + PullRequestQuery.quote(value.toString());
	}

}
