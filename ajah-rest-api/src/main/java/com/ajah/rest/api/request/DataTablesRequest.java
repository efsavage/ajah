/*
 *  Copyright 2013-2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.rest.api.request;

import lombok.Data;

import com.ajah.util.StringUtils;

/**
 * A bean that deals with the quirky column names of the <a
 * href="http://datatables.net/">DataTables</a> plugin.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
public class DataTablesRequest {

	private int iDisplayStart = 0;
	private int iDisplayLength = 10;
	private String sSearch = null;
	private String sEcho;
	private Integer iSortCol_0;
	private String sSortDir_0;
	private String mDataProp_0;
	private String mDataProp_1;
	private String mDataProp_2;
	private String mDataProp_3;
	private String mDataProp_4;
	private String mDataProp_5;
	private String mDataProp_6;
	private String mDataProp_7;

	private String sortColumn;

	public String getSortColumn() {
		if (StringUtils.isBlank(this.sortColumn) && this.iSortCol_0 != null) {
			if (this.iSortCol_0.intValue() == 0) {
				return this.mDataProp_0;
			} else if (this.iSortCol_0.intValue() == 1) {
				return this.mDataProp_1;
			} else if (this.iSortCol_0.intValue() == 2) {
				return this.mDataProp_2;
			} else if (this.iSortCol_0.intValue() == 3) {
				return this.mDataProp_3;
			} else if (this.iSortCol_0.intValue() == 4) {
				return this.mDataProp_4;
			} else if (this.iSortCol_0.intValue() == 5) {
				return this.mDataProp_5;
			} else if (this.iSortCol_0.intValue() == 6) {
				return this.mDataProp_6;
			} else if (this.iSortCol_0.intValue() == 7) {
				return this.mDataProp_7;
			}
		}
		return this.sortColumn;
	}

	public void setLength(final int length) {
		setIDisplayLength(length);
	}

	public void setStart(final int start) {
		setIDisplayStart(start);
	}

}
