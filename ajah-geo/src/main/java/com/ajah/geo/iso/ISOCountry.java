/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.geo.iso;

import com.ajah.geo.Country;
import com.ajah.util.IdentifiableEnum;

/**
 * This is the <a
 * href="http://www.iso.org/iso/english_country_names_and_code_elements"
 * >ISO-3166</a> implementation of the Country interface, which should be enough
 * for most uses of Country.
 * 
 * It is true that this does duplicate data that already exists in Java's Locale
 * mechanisms, but I don't like the idea of a country being added or removed in
 * a future version breaking my application.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum ISOCountry implements Country, IdentifiableEnum<String> {

	/** Andorra */
	AD("ad", "AND", "Andorra"),
	/** United Arab Emirates */
	AE("ae", "ARE", "United Arab Emirates"),
	/** Afghanistan */
	AF("af", "AFG", "Afghanistan"),
	/** Antigua and Barbuda */
	AG("ag", "ATG", "Antigua and Barbuda"),
	/** Anguilla */
	AI("ai", "AIA", "Anguilla"),
	/** Albania */
	AL("al", "ALB", "Albania"),
	/** Armenia */
	AM("am", "ARM", "Armenia"),
	/** Netherlands Antilles */
	AN("an", "ANT", "Netherlands Antilles"),
	/** Angola */
	AO("ao", "AGO", "Angola"),
	/** Antarctica */
	AQ("aq", "ATA", "Antarctica"),
	/** Argentina */
	AR("ar", "ARG", "Argentina"),
	/** American Samoa */
	AS("as", "ASM", "American Samoa"),
	/** Austria */
	AT("at", "AUT", "Austria"),
	/** Australia */
	AU("au", "AUS", "Australia"),
	/** Aruba */
	AW("aw", "ABW", "Aruba"),
	/** &Aring;land Islands */
	AX("ax", "ALA", "Åland Islands"),
	/** Azerbaijan */
	AZ("az", "AZE", "Azerbaijan"),
	/** Bosnia and Herzegovina */
	BA("ba", "BIH", "Bosnia and Herzegovina"),
	/** Barbados */
	BB("bb", "BRB", "Barbados"),
	/** Bangladesh */
	BD("bd", "BGD", "Bangladesh"),
	/** Belgium */
	BE("be", "BEL", "Belgium"),
	/** Burkina Faso */
	BF("bf", "BFA", "Burkina Faso"),
	/** Bulgaria */
	BG("bg", "BGR", "Bulgaria"),
	/** Bahrain */
	BH("bh", "BHR", "Bahrain"),
	/** Burundi */
	BI("bi", "BDI", "Burundi"),
	/** Benin */
	BJ("bj", "BEN", "Benin"),
	/** Saint Barth&eacute;lemy */
	BL("bl", "BLM", "Saint Barthélemy"),
	/** Bermuda */
	BM("bm", "BMU", "Bermuda"),
	/** Brunei */
	BN("bn", "BRN", "Brunei"),
	/** Bolivia */
	BO("bo", "BOL", "Bolivia"),
	/** Brazil */
	BR("br", "BRA", "Brazil"),
	/** Bahamas */
	BS("bs", "BHS", "Bahamas"),
	/** Bhutan */
	BT("bt", "BTN", "Bhutan"),
	/** Bouvet Island */
	BV("bv", "BVT", "Bouvet Island"),
	/** Botswana */
	BW("bw", "BWA", "Botswana"),
	/** Belarus */
	BY("by", "BLR", "Belarus"),
	/** Belize */
	BZ("bz", "BLZ", "Belize"),
	/** Canada */
	CA("ca", "CAN", "Canada"),
	/** Cocos Islands */
	CC("cc", "CCK", "Cocos Islands"),
	/** The Democratic Republic Of Congo */
	CD("cd", "COD", "The Democratic Republic Of Congo"),
	/** Central African Republic */
	CF("cf", "CAF", "Central African Republic"),
	/** Congo */
	CG("cg", "COG", "Congo"),
	/** Switzerland */
	CH("ch", "CHE", "Switzerland"),
	/** C&ocirc;te d'Ivoire */
	CI("ci", "CIV", "Côte d'Ivoire"),
	/** Cook Islands */
	CK("ck", "COK", "Cook Islands"),
	/** Chile */
	CL("cl", "CHL", "Chile"),
	/** Cameroon */
	CM("cm", "CMR", "Cameroon"),
	/** China */
	CN("cn", "CHN", "China"),
	/** Colombia */
	CO("co", "COL", "Colombia"),
	/** Costa Rica */
	CR("cr", "CRI", "Costa Rica"),
	/** Serbia and Montenegro */
	CS("cs", "SCG", "Serbia and Montenegro"),
	/** Cuba */
	CU("cu", "CUB", "Cuba"),
	/** Cape Verde */
	CV("cv", "CPV", "Cape Verde"),
	/** Christmas Island */
	CX("cx", "CXR", "Christmas Island"),
	/** Cyprus */
	CY("cy", "CYP", "Cyprus"),
	/** Czech Republic */
	CZ("cz", "CZE", "Czech Republic"),
	/** Germany */
	DE("de", "DEU", "Germany"),
	/** Djibouti */
	DJ("dj", "DJI", "Djibouti"),
	/** Denmark */
	DK("dk", "DNK", "Denmark"),
	/** Dominica */
	DM("dm", "DMA", "Dominica"),
	/** Dominican Republic */
	DO("do", "DOM", "Dominican Republic"),
	/** Algeria */
	DZ("dz", "DZA", "Algeria"),
	/** Ecuador */
	EC("ec", "ECU", "Ecuador"),
	/** Estonia */
	EE("ee", "EST", "Estonia"),
	/** Egypt */
	EG("eg", "EGY", "Egypt"),
	/** Western Sahara */
	EH("eh", "ESH", "Western Sahara"),
	/** Eritrea */
	ER("er", "ERI", "Eritrea"),
	/** Spain */
	ES("es", "ESP", "Spain"),
	/** Ethiopia */
	ET("et", "ETH", "Ethiopia"),
	/**
	 * European Union
	 * 
	 * <strong>NOT STANDARD</strong>
	 * */
	EU("eu", "EUR", "European Union"),
	/** Finland */
	FI("fi", "FIN", "Finland"),
	/** Fiji */
	FJ("fj", "FJI", "Fiji"),
	/** Falkland Islands */
	FK("fk", "FLK", "Falkland Islands"),
	/** Micronesia */
	FM("fm", "FSM", "Micronesia"),
	/** Faroe Islands */
	FO("fo", "FRO", "Faroe Islands"),
	/** France */
	FR("fr", "FRA", "France"),
	/** Gabon */
	GA("ga", "GAB", "Gabon"),
	/** United Kingdom */
	GB("gb", "GBR", "United Kingdom"),
	/** Channel Islands (subdivision of United Kingdom) */
	GB_CHA("gb-cha", "GB-CHA", "Channel Islands", GB),
	/** England (subdivision of United Kingdom) */
	GB_ENG("gb-eng", "GB-ENG", "England", GB),
	/** Isle of Man (subdivision of United Kingdom) */
	GB_IOM("gb-iom", "GB-IOM", "Isle of Man", GB),
	/** Northern Ireland (subdivision of United Kingdom) */
	GB_NIR("gb-nir", "GB-NIR", "Northern Ireland", GB),
	/** Scotland (subdivision of United Kingdom) */
	GB_SCT("gb-sct", "GB-SCT", "Scotland", GB),
	/** Wales (subdivision of United Kingdom) */
	GB_WLS("gb-wls", "GB-WLS", "Wales", GB),
	/** Grenada */
	GD("gd", "GRD", "Grenada"),
	/** Georgia */
	GE("ge", "GEO", "Georgia"),
	/** French Guiana */
	GF("gf", "GUF", "French Guiana"),
	/** Guernsey */
	GG("gg", "GGY", "Guernsey"),
	/** Ghana */
	GH("gh", "GHA", "Ghana"),
	/** Gibraltar */
	GI("gi", "GIB", "Gibraltar"),
	/** Greenland */
	GL("gl", "GRL", "Greenland"),
	/** Gambia */
	GM("gm", "GMB", "Gambia"),
	/** Guinea */
	GN("gn", "GIN", "Guinea"),
	/** Guadeloupe */
	GP("gp", "GLP", "Guadeloupe"),
	/** Equatorial Guinea */
	GQ("gq", "GNQ", "Equatorial Guinea"),
	/** Greece */
	GR("gr", "GRC", "Greece"),
	/** South Georgia And The South Sandwich Islands */
	GS("gs", "SGS", "South Georgia And The South Sandwich Islands"),
	/** Guatemala */
	GT("gt", "GTM", "Guatemala"),
	/** Guam */
	GU("gu", "GUM", "Guam"),
	/** Guinea-Bissau */
	GW("gw", "GNB", "Guinea-Bissau"),
	/** Guyana */
	GY("gy", "GUY", "Guyana"),
	/** Hong Kong */
	HK("hk", "HKG", "Hong Kong"),
	/** Heard Island And McDonald Islands */
	HM("hm", "HMD", "Heard Island And McDonald Islands"),
	/** Honduras */
	HN("hn", "HND", "Honduras"),
	/** Croatia */
	HR("hr", "HRV", "Croatia"),
	/** Haiti */
	HT("ht", "HTI", "Haiti"),
	/** Hungary */
	HU("hu", "HUN", "Hungary"),
	/** Indonesia */
	ID("id", "IDN", "Indonesia"),
	/** Ireland */
	IE("ie", "IRL", "Ireland"),
	/** Israel */
	IL("il", "ISR", "Israel"),
	/** Isle Of Man */
	IM("im", "IMN", "Isle Of Man"),
	/** India */
	IN("in", "IND", "India"),
	/** British Indian Ocean Territory */
	IO("io", "IOT", "British Indian Ocean Territory"),
	/** Iraq */
	IQ("iq", "IRQ", "Iraq"),
	/** Iran */
	IR("ir", "IRN", "Iran"),
	/** Iceland */
	IS("is", "ISL", "Iceland"),
	/** Italy */
	IT("it", "ITA", "Italy"),
	/** Jersey */
	JE("je", "JEY", "Jersey"),
	/** Jamaica */
	JM("jm", "JAM", "Jamaica"),
	/** Jordan */
	JO("jo", "JOR", "Jordan"),
	/** Japan */
	JP("jp", "JPN", "Japan"),
	/** Kenya */
	KE("ke", "KEN", "Kenya"),
	/** Kyrgyzstan */
	KG("kg", "KGZ", "Kyrgyzstan"),
	/** Cambodia */
	KH("kh", "KHM", "Cambodia"),
	/** Kiribati */
	KI("ki", "KIR", "Kiribati"),
	/** Comoros */
	KM("km", "COM", "Comoros"),
	/** Saint Kitts And Nevis */
	KN("kn", "KNA", "Saint Kitts And Nevis"),
	/** North Korea */
	KP("kp", "PRK", "North Korea"),
	/** South Korea */
	KR("kr", "KOR", "South Korea"),
	/** Kuwait */
	KW("kw", "KWT", "Kuwait"),
	/** Cayman Islands */
	KY("ky", "CYM", "Cayman Islands"),
	/** Kazakhstan */
	KZ("kz", "KAZ", "Kazakhstan"),
	/** Laos */
	LA("la", "LAO", "Laos"),
	/** Lebanon */
	LB("lb", "LBN", "Lebanon"),
	/** Saint Lucia */
	LC("lc", "LCA", "Saint Lucia"),
	/** Liechtenstein */
	LI("li", "LIE", "Liechtenstein"),
	/** Sri Lanka */
	LK("lk", "LKA", "Sri Lanka"),
	/** Liberia */
	LR("lr", "LBR", "Liberia"),
	/** Lesotho */
	LS("ls", "LSO", "Lesotho"),
	/** Lithuania */
	LT("lt", "LTU", "Lithuania"),
	/** Luxembourg */
	LU("lu", "LUX", "Luxembourg"),
	/** Latvia */
	LV("lv", "LVA", "Latvia"),
	/** Libya */
	LY("ly", "LBY", "Libya"),
	/** Morocco */
	MA("ma", "MAR", "Morocco"),
	/** Monaco */
	MC("mc", "MCO", "Monaco"),
	/** Moldova */
	MD("md", "MDA", "Moldova"),
	/** Montenegro */
	ME("me", "MNE", "Montenegro"),
	/** Saint Martin */
	MF("mf", "MAF", "Saint Martin"),
	/** Madagascar */
	MG("mg", "MDG", "Madagascar"),
	/** Marshall Islands */
	MH("mh", "MHL", "Marshall Islands"),
	/** Macedonia */
	MK("mk", "MKD", "Macedonia"),
	/** Mali */
	ML("ml", "MLI", "Mali"),
	/** Myanmar */
	MM("mm", "MMR", "Myanmar"),
	/** Mongolia */
	MN("mn", "MNG", "Mongolia"),
	/** Macao */
	MO("mo", "MAC", "Macao"),
	/** Northern Mariana Islands */
	MP("mp", "MNP", "Northern Mariana Islands"),
	/** Martinique */
	MQ("mq", "MTQ", "Martinique"),
	/** Mauritania */
	MR("mr", "MRT", "Mauritania"),
	/** Montserrat */
	MS("ms", "MSR", "Montserrat"),
	/** Malta */
	MT("mt", "MLT", "Malta"),
	/** Mauritius */
	MU("mu", "MUS", "Mauritius"),
	/** Maldives */
	MV("mv", "MDV", "Maldives"),
	/** Malawi */
	MW("mw", "MWI", "Malawi"),
	/** Mexico */
	MX("mx", "MEX", "Mexico"),
	/** Malaysia */
	MY("my", "MYS", "Malaysia"),
	/** Mozambique */
	MZ("mz", "MOZ", "Mozambique"),
	/** Namibia */
	NA("na", "NAM", "Namibia"),
	/** New Caledonia */
	NC("nc", "NCL", "New Caledonia"),
	/** Niger */
	NE("ne", "NER", "Niger"),
	/** Norfolk Island */
	NF("nf", "NFK", "Norfolk Island"),
	/** Nigeria */
	NG("ng", "NGA", "Nigeria"),
	/** Nicaragua */
	NI("ni", "NIC", "Nicaragua"),
	/** Netherlands */
	NL("nl", "NLD", "Netherlands"),
	/** Norway */
	NO("no", "NOR", "Norway"),
	/** Nepal */
	NP("np", "NPL", "Nepal"),
	/** Nauru */
	NR("nr", "NRU", "Nauru"),
	/** Niue */
	NU("nu", "NIU", "Niue"),
	/** New Zealand */
	NZ("nz", "NZL", "New Zealand"),
	/** Oman */
	OM("om", "OMN", "Oman"),
	/** Panama */
	PA("pa", "PAN", "Panama"),
	/** Peru */
	PE("pe", "PER", "Peru"),
	/** French Polynesia */
	PF("pf", "PYF", "French Polynesia"),
	/** Papua New Guinea */
	PG("pg", "PNG", "Papua New Guinea"),
	/** Philippines */
	PH("ph", "PHL", "Philippines"),
	/** Pakistan */
	PK("pk", "PAK", "Pakistan"),
	/** Poland */
	PL("pl", "POL", "Poland"),
	/** Saint Pierre And Miquelon */
	PM("pm", "SPM", "Saint Pierre And Miquelon"),
	/** Pitcairn */
	PN("pn", "PCN", "Pitcairn"),
	/** Puerto Rico */
	PR("pr", "PRI", "Puerto Rico"),
	/** Palestine */
	PS("ps", "PSE", "Palestine"),
	/** Portugal */
	PT("pt", "PRT", "Portugal"),
	/** Palau */
	PW("pw", "PLW", "Palau"),
	/** Paraguay */
	PY("py", "PRY", "Paraguay"),
	/** Qatar */
	QA("qa", "QAT", "Qatar"),
	/** Reunion */
	RE("re", "REU", "Reunion"),
	/** Romania */
	RO("ro", "ROU", "Romania"),
	/** Serbia */
	RS("rs", "SRB", "Serbia"),
	/** Russia */
	RU("ru", "RUS", "Russia"),
	/** Rwanda */
	RW("rw", "RWA", "Rwanda"),
	/** Saudi Arabia */
	SA("sa", "SAU", "Saudi Arabia"),
	/** Solomon Islands */
	SB("sb", "SLB", "Solomon Islands"),
	/** Seychelles */
	SC("sc", "SYC", "Seychelles"),
	/** Sudan */
	SD("sd", "SDN", "Sudan"),
	/** Sweden */
	SE("se", "SWE", "Sweden"),
	/** Singapore */
	SG("sg", "SGP", "Singapore"),
	/** Saint Helena */
	SH("sh", "SHN", "Saint Helena"),
	/** Slovenia */
	SI("si", "SVN", "Slovenia"),
	/** Svalbard And Jan Mayen */
	SJ("sj", "SJM", "Svalbard And Jan Mayen"),
	/** Slovakia */
	SK("sk", "SVK", "Slovakia"),
	/** Sierra Leone */
	SL("sl", "SLE", "Sierra Leone"),
	/** San Marino */
	SM("sm", "SMR", "San Marino"),
	/** Senegal */
	SN("sn", "SEN", "Senegal"),
	/** Somalia */
	SO("so", "SOM", "Somalia"),
	/** Suriname */
	SR("sr", "SUR", "Suriname"),
	/** Sao Tome And Principe */
	ST("st", "STP", "Sao Tome And Principe"),
	/** El Salvador */
	SV("sv", "SLV", "El Salvador"),
	/** Syria */
	SY("sy", "SYR", "Syria"),
	/** Swaziland */
	SZ("sz", "SWZ", "Swaziland"),
	/** Turks And Caicos Islands */
	TC("tc", "TCA", "Turks And Caicos Islands"),
	/** Chad */
	TD("td", "TCD", "Chad"),
	/** French Southern Territories */
	TF("tf", "ATF", "French Southern Territories"),
	/** Togo */
	TG("tg", "TGO", "Togo"),
	/** Thailand */
	TH("th", "THA", "Thailand"),
	/** Tajikistan */
	TJ("tj", "TJK", "Tajikistan"),
	/** Tokelau */
	TK("tk", "TKL", "Tokelau"),
	/** Timor-Leste */
	TL("tl", "TLS", "Timor-Leste"),
	/** Turkmenistan */
	TM("tm", "TKM", "Turkmenistan"),
	/** Tunisia */
	TN("tn", "TUN", "Tunisia"),
	/** Tonga */
	TO("to", "TON", "Tonga"),
	/** Turkey */
	TR("tr", "TUR", "Turkey"),
	/** Trinidad and Tobago */
	TT("tt", "TTO", "Trinidad and Tobago"),
	/** Tuvalu */
	TV("tv", "TUV", "Tuvalu"),
	/** Taiwan */
	TW("tw", "TWN", "Taiwan"),
	/** Tanzania */
	TZ("tz", "TZA", "Tanzania"),
	/** Ukraine */
	UA("ua", "UKR", "Ukraine"),
	/** Uganda */
	UG("ug", "UGA", "Uganda"),
	/** United States Minor Outlying Islands */
	UM("um", "UMI", "United States Minor Outlying Islands"),
	/** United States */
	US("us", "USA", "United States"),
	/** Uruguay */
	UY("uy", "URY", "Uruguay"),
	/** Uzbekistan */
	UZ("uz", "UZB", "Uzbekistan"),
	/** Vatican */
	VA("va", "VAT", "Vatican"),
	/** Saint Vincent And The Grenadines */
	VC("vc", "VCT", "Saint Vincent And The Grenadines"),
	/** Venezuela */
	VE("ve", "VEN", "Venezuela"),
	/** British Virgin Islands */
	VG("vg", "VGB", "British Virgin Islands"),
	/** U.S. Virgin Islands */
	VI("vi", "VIR", "U.S. Virgin Islands"),
	/** Vietnam */
	VN("vn", "VNM", "Vietnam"),
	/** Vanuatu */
	VU("vu", "VUT", "Vanuatu"),
	/** Wallis And Futuna */
	WF("wf", "WLF", "Wallis And Futuna"),
	/** Samoa */
	WS("ws", "WSM", "Samoa"),
	/** Yemen */
	YE("ye", "YEM", "Yemen"),
	/** Mayotte */
	YT("yt", "MYT", "Mayotte"),
	/** South Africa */
	ZA("za", "ZAF", "South Africa"),
	/** Zambia */
	ZM("zm", "ZMB", "Zambia"),
	/** Zimbabwe */
	ZW("zw", "ZWE", "Zimbabwe");

	/**
	 * Finds a PlayerType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching PlayerType, or null.
	 */
	public static ISOCountry get(final String string) {
		for (final ISOCountry type : values()) {
			if (type.getId().equals(string) || type.getCode().equals(string) || type.name().equals(string) || type.getName().equals(string)) {
				return type;
			}
		}
		return null;
	}

	private final String id;

	private final String abbr2;

	private final String abbr3;

	private final String name;

	private final ISOCountry parent;

	private ISOCountry(final String id, final String abbr3, final String name) {
		this.id = id;
		this.abbr2 = id.toUpperCase();
		this.abbr3 = abbr3;
		this.name = name;
		this.parent = null;
	}

	private ISOCountry(final String id, final String abbr3, final String name, final ISOCountry parent) {
		this.id = id;
		this.abbr2 = id.toUpperCase();
		this.abbr3 = abbr3;
		this.name = name;
		this.parent = parent;
	}

	/**
	 * The public abbreviation of the country. Alias for
	 * {@link ISOCountry#getAbbr2}.
	 * 
	 * @see com.ajah.geo.iso.ISOCountry#getAbbr2()
	 * 
	 *      Example: The abbreviation of the United States would be "US".
	 * 
	 * @return The public abbreviation of the country. Should never be null or
	 *         empty.
	 */
	@Override
	public String getAbbr() {
		return getAbbr2();
	}

	/**
	 * The ISO 2-letter code for this country.
	 * 
	 * Example: The abbreviation of the United States would be "US".
	 * 
	 * @return The ISO 2-letter code for the country. Should never be null or
	 *         empty.
	 */
	public String getAbbr2() {
		return this.abbr2;
	}

	/**
	 * The ISO 3-letter code for this country.
	 * 
	 * Example: The abbreviation of the United States would be "USA".
	 * 
	 * @return The ISO 3-letter code for the country. Should never be null or
	 *         empty.
	 */
	public String getAbbr3() {
		return this.abbr3;
	}

	/**
	 * @see com.ajah.util.IdentifiableEnum#getCode()
	 */
	@Override
	public String getCode() {
		return getAbbr3();
	}

	/**
	 * The lowercase version of the ISO 2-letter code for this country.
	 * 
	 * Example: The ID of the United States would be "us".
	 * 
	 * @return The lowercase version of the ISO 2-letter code for the country.
	 *         Should never be null or empty.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The ISO "short" name of the country.
	 * 
	 * @see com.ajah.geo.Country#getName()
	 * @return the ISO Short name of the country. Should never be null or empty.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the parent of this country (making it a subdivision).
	 * 
	 * @return the parent Country, if applicable, otherwise null.
	 */
	public ISOCountry getParent() {
		return this.parent;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
