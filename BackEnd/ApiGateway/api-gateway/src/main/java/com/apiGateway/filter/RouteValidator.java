package com.apiGateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of(
			"/userAuth/user-login",
			"/userAuth/register-user",
			"/userAuth/logout",
			"userAuth/forget-password"
			);

	public Predicate<ServerHttpRequest> isOpenEndPoint = request -> openApiEndpoints.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));

	public static final List<String> hrApiEndpoints = List.of(
			"/userManager/get-user-details",
			"/userManager/change-password",
			"/userManager/update-profile",
			"/userManager/delete-user",
			"/feedbackManager/get-all-feedbacks-hr",
			"/feedbackManager/handle-feedback-document",
			"/feedbackManager/download-document",
			"/feedbackManager/get-feedback-by-documentNo",
			"/feedbackManager/delete-feedback",
			"/feedbackManager/get-hiring-data"
			);

	public Predicate<ServerHttpRequest> isEndPointForHR = request -> hrApiEndpoints.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));
	
	public static final List<String> interviewerApiEndpoints = List.of(
			"/userManager/get-user-details",
			"/userManager/change-password",
			"/userManager/forget-password",
			"/userManager/update-profile",
			"/feedbackManager/handle-feedback-form",
			"/feedbackManager/handle-feedback-document",
			"/feedbackManager/download-document",
			"/feedbackManager/get-feedback-by-documentNo",
			"/feedbackManager/get-all-feedbacks-interviewer",
			"/feedbackManager/update-feedback-details",
			"/feedbackManager/delete-feedback",
			"/feedbackManager/get-all-feedback-files-by-interviewer",
			"/feedbackManager/delete-feedback-file",
			"/feedbackManager/get-hiring-data"
			);

	public Predicate<ServerHttpRequest> isEndPointForInterviewer = request -> interviewerApiEndpoints.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));
}
