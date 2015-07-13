/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.swagger;

/**
 * Controller for Swagger documentation.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
// @Controller
public class DocController {
	//
	// /**
	// * The home page.
	// *
	// * @param request
	// * Request object, required.
	// * @return "v1/home"
	// */
	// @RequestMapping("/pub/api-docs")
	// @ResponseBody
	// public SwaggerMainResponse home(final HttpServletRequest request) {
	// SwaggerMainResponse swagger = new SwaggerMainResponse();
	//
	// swagger.apis = new ArrayList<>();
	// swagger.apis.add(new SwaggerApiShort("/publication",
	// "Single publication"));
	// swagger.apis.add(new SwaggerApiShort("/publication-list",
	// "Lists publications with simple filters."));
	// // swagger.apis.add(new SwaggerApiShort("/publication-search",
	// // "Searches publications with advanced filters."));
	//
	// return swagger;
	// }
	//
	// @RequestMapping("/pub/api-docs/publication")
	// @ResponseBody
	// public SwaggerResponse publication() {
	// SwaggerResponse swagger = new SwaggerResponse();
	// swagger.resourcePath = "publication";
	//
	// SwaggerApi publicationApi = new
	// SwaggerApi("/publication/{publicationId}",
	// "Returns a single publication.");
	// swagger.apis = new ArrayList<>();
	// swagger.apis.add(publicationApi);
	//
	// SwaggerOperation publicationOperation = new SwaggerOperation();
	// publicationOperation.responseClass = "PublicationResponse";
	// publicationApi.operations.add(publicationOperation);
	// {
	// SwaggerParameter publicationId = new SwaggerParameter("publicationId",
	// "string",
	// "The unique ID of the publication, assigned by the Zmags Publicator.");
	// publicationId.setRequired(true);
	// publicationId.setParamType("path");
	// publicationOperation.parameters.add(publicationId);
	// }
	// {
	// SwaggerParameter pageFrom = new SwaggerParameter("pageFrom", "int",
	// "The first page of the publication to return.  The first page of the publication is page 1.");
	// pageFrom.setMinimum(new Integer(1));
	// pageFrom.setDefaultValue("1");
	// publicationOperation.parameters.add(pageFrom);
	// }
	// {
	// SwaggerParameter pageTo = new SwaggerParameter("pageTo", "int",
	// "The last page of the publication to return.  Must be greater than or equal to pageFrom.  Defaults to pageFrom + 9 (10 page window).");
	// pageTo.setMinimum(new Integer(1));
	// pageTo.setDefaultValue("10");
	// publicationOperation.parameters.add(pageTo);
	// }
	//
	// addErrors(publicationOperation.responseMessages);
	//
	// SwaggerModel publicationResponseModel = new
	// SwaggerModel("PublicationResponse");
	// SwaggerModelProperty publicationProp = new
	// SwaggerModelProperty("Publication", false);
	// publicationResponseModel.getProperties().put("publication",
	// publicationProp);
	//
	// SwaggerModelProperty errorsProp = new SwaggerModelProperty("array",
	// false);
	// errorsProp.items = new SwaggerItemsRef("ResponseError");
	// publicationResponseModel.getProperties().put("errors", errorsProp);
	//
	// swagger.models.put(publicationResponseModel.getId(),
	// publicationResponseModel);
	//
	// addPublicationModel(swagger.models);
	// addErrorModel(swagger.models);
	//
	// return swagger;
	// }
	//
	// @RequestMapping("/pub/api-docs/publication-list")
	// @ResponseBody
	// public SwaggerResponse publicationList() {
	// SwaggerResponse swagger = new SwaggerResponse();
	// swagger.resourcePath = "publication/list";
	//
	// SwaggerApi publicationListApi = new SwaggerApi("/publication-list",
	// "Lists publications with simple filters.");
	// swagger.apis = new ArrayList<>();
	// swagger.apis.add(publicationListApi);
	//
	// SwaggerOperation publicationOperation = new SwaggerOperation();
	// publicationOperation.responseClass = "PublicationListResponse";
	// publicationListApi.operations.add(publicationOperation);
	//
	// {
	// SwaggerParameter param = new SwaggerParameter("page", "int",
	// "The page of results to return.  The first page of results is page 0.");
	// param.setMinimum(new Integer(0));
	// param.setDefaultValue("0");
	// publicationOperation.parameters.add(param);
	// }
	// {
	// SwaggerParameter param = new SwaggerParameter("count", "int",
	// "The number of results to return per page.");
	// param.setMinimum(new Integer(1));
	// param.setDefaultValue("10");
	// publicationOperation.parameters.add(param);
	// }
	// {
	// SwaggerParameter param = new SwaggerParameter("status", "string",
	// "The status of publications to return.");
	// param.setAllowMultiple(true);
	// List<String> enumValues = new ArrayList<>();
	// for (PublicationStatus status : PublicationStatus.values()) {
	// if (status.isApiAvailable()) {
	// enumValues.add(status.getCode());
	// }
	// }
	// param.setEnumValues(enumValues);
	// publicationOperation.parameters.add(param);
	// }
	// {
	// SwaggerParameter pageFrom = new SwaggerParameter("includePageFrom",
	// "int",
	// "The first page of each publication in the list to return.  This is most useful for 'bookshelf' style search results where the first page is displayed.  The first page of the publication is page 1.");
	// pageFrom.setMinimum(new Integer(1));
	// pageFrom.setDefaultValue("1");
	// publicationOperation.parameters.add(pageFrom);
	// }
	// {
	// SwaggerParameter pageTo = new SwaggerParameter("includePageTo", "int",
	// "The last page of each publication to return.  Must be greater than or equal to pageFrom.  Defaults to pageFrom + 9 (10 page window).");
	// pageTo.setMinimum(new Integer(1));
	// pageTo.setDefaultValue("10");
	// publicationOperation.parameters.add(pageTo);
	// }
	//
	// addErrors(publicationOperation.responseMessages);
	// addPublicationListModel(swagger.models);
	// addPublicationModel(swagger.models);
	// addErrorModel(swagger.models);
	//
	// return swagger;
	// }
	//
	// private static void addPublicationListModel(Map<String, SwaggerModel>
	// models) {
	// SwaggerModel publicationResponseModel = new
	// SwaggerModel("PublicationListResponse");
	// SwaggerModelProperty publicationProp = new SwaggerModelProperty("array",
	// false);
	// publicationProp.items = new SwaggerItemsRef("Publication");
	// publicationResponseModel.getProperties().put("publications",
	// publicationProp);
	//
	// SwaggerModelProperty errorsProp = new SwaggerModelProperty("array",
	// false);
	// errorsProp.items = new SwaggerItemsRef("ResponseError");
	// publicationResponseModel.getProperties().put("errors", errorsProp);
	//
	// models.put(publicationResponseModel.getId(), publicationResponseModel);
	// }
	//
	// @RequestMapping("/pub/api-docs/publication-search")
	// @ResponseBody
	// public SwaggerResponse publicationSearch() {
	// SwaggerResponse swagger = new SwaggerResponse();
	// swagger.resourcePath = "publication/list";
	//
	// SwaggerApi publicationSearchApi = new SwaggerApi("/publication-search",
	// "Searches publications with advanced filters.");
	// swagger.apis = new ArrayList<>();
	// swagger.apis.add(publicationSearchApi);
	//
	// SwaggerOperation publicationOperation = new SwaggerOperation();
	// publicationOperation.responseClass = "PublicationListResponse";
	// publicationSearchApi.operations.add(publicationOperation);
	//
	// addErrors(publicationOperation.responseMessages);
	// addPublicationListModel(swagger.models);
	// addPublicationModel(swagger.models);
	// addErrorModel(swagger.models);
	//
	// return swagger;
	// }
	//
	// private static void addErrorModel(Map<String, SwaggerModel> models) {
	// SwaggerModel responseError = new SwaggerModel("ResponseError");
	// SwaggerModelProperty errorCode = new SwaggerModelProperty("int", true);
	// responseError.getProperties().put("code", errorCode);
	// SwaggerModelProperty errorMessage = new SwaggerModelProperty("string",
	// true);
	// responseError.getProperties().put("message", errorMessage);
	// models.put(responseError.getId(), responseError);
	// }
	//
	// private static void addPublicationModel(Map<String, SwaggerModel> models)
	// {
	// SwaggerModel publicationModel = new SwaggerModel("Publication");
	// models.put(publicationModel.getId(), publicationModel);
	//
	// publicationModel.getProperties().put("id", new
	// SwaggerModelProperty("string", true));
	// publicationModel.getProperties().put("status", new
	// SwaggerModelProperty("PublicationStatus", true));
	// publicationModel.getProperties().put("type", new
	// SwaggerModelProperty("PublicationType", true));
	// publicationModel.getProperties().put("layout", new
	// SwaggerModelProperty("PublicationLayout", true));
	// publicationModel.getProperties().put("pageCount", new
	// SwaggerModelProperty("int", true));
	// publicationModel.getProperties().put("version", new
	// SwaggerModelProperty("int", true));
	//
	// SwaggerModelProperty descriptorsProp = new SwaggerModelProperty("array",
	// false);
	// descriptorsProp.items = new SwaggerItemsRef("Descriptor");
	// publicationModel.getProperties().put("descriptors", descriptorsProp);
	//
	// SwaggerModelProperty pagesProp = new SwaggerModelProperty("array",
	// false);
	// pagesProp.items = new SwaggerItemsRef("Page");
	// publicationModel.getProperties().put("pages", pagesProp);
	// }
	//
	// private static void addErrors(List<SwaggerResponseMessage>
	// responseMessages) {
	// responseMessages.add(new
	// SwaggerResponseMessage(HttpStatus.BAD_REQUEST.value(),
	// RuralResponseError.MISSING_PARAM, "Missing parameter"));
	// responseMessages.add(new
	// SwaggerResponseMessage(HttpStatus.FORBIDDEN.value(),
	// RuralResponseError.INVALID_API_KEY, "Invalid API Key"));
	// responseMessages.add(new
	// SwaggerResponseMessage(HttpStatus.FORBIDDEN.value(),
	// RuralResponseError.PERMISSION_DENIED, "Permission Denied"));
	// responseMessages.add(new
	// SwaggerResponseMessage(HttpStatus.FORBIDDEN.value(),
	// RuralResponseError.PROTOCOL_NOT_SUPPORTED, "Protocol not supported"));
	// responseMessages.add(new
	// SwaggerResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
	// RuralResponseError.DATA_OPERATION_ERROR, "Data Operation Error"));
	// responseMessages.add(new
	// SwaggerResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
	// RuralResponseError.UNEXPECTED_ERROR, "Unexpected Error"));
	// }

}
