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

import com.ajah.geo.Continent;
import com.ajah.geo.Country;
import com.ajah.util.IdentifiableEnum;

import lombok.Getter;

/**
 * This is the
 * <a href="http://www.iso.org/iso/english_country_names_and_code_elements" >ISO
 * -3166</a> implementation of the Country interface, which should be enough for
 * most uses of Country.
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
	AD("ad", "AND", "Andorra", Continent.EUROPE),
	/** United Arab Emirates */
	AE("ae", "ARE", "United Arab Emirates", Continent.ASIA),
	/** Afghanistan */
	AF("af", "AFG", "Afghanistan", Continent.ASIA),
	/** Antigua and Barbuda */
	AG("ag", "ATG", "Antigua and Barbuda", Continent.NORTH_AMERICA),
	/** Anguilla */
	AI("ai", "AIA", "Anguilla", Continent.NORTH_AMERICA),
	/** Albania */
	AL("al", "ALB", "Albania", Continent.EUROPE),
	/** Armenia */
	AM("am", "ARM", "Armenia", Continent.ASIA),
	/** Netherlands Antilles */
	AN("an", "ANT", "Netherlands Antilles", Continent.SOUTH_AMERICA),
	/** Angola */
	AO("ao", "AGO", "Angola", Continent.AFRICA),
	/** Antarctica */
	AQ("aq", "ATA", "Antarctica", Continent.ANTARTICTICA),
	/** Argentina */
	AR("ar", "ARG", "Argentina", Continent.SOUTH_AMERICA),
	/** American Samoa */
	AS("as", "ASM", "American Samoa", Continent.OCEANIA),
	/** Austria */
	AT("at", "AUT", "Austria", Continent.EUROPE),
	/** Australia */
	AU("au", "AUS", "Australia", Continent.OCEANIA),
	/** Aruba */
	AW("aw", "ABW", "Aruba", Continent.SOUTH_AMERICA),
	/** &Aring;land Islands */
	AX("ax", "ALA", "Åland Islands", Continent.EUROPE),
	/** Azerbaijan */
	AZ("az", "AZE", "Azerbaijan", Continent.ASIA),
	/** Bosnia and Herzegovina */
	BA("ba", "BIH", "Bosnia and Herzegovina", Continent.EUROPE),
	/** Barbados */
	BB("bb", "BRB", "Barbados", Continent.NORTH_AMERICA),
	/** Bangladesh */
	BD("bd", "BGD", "Bangladesh", Continent.ASIA),
	/** Belgium */
	BE("be", "BEL", "Belgium", Continent.EUROPE),
	/** Burkina Faso */
	BF("bf", "BFA", "Burkina Faso", Continent.AFRICA),
	/** Bulgaria */
	BG("bg", "BGR", "Bulgaria", Continent.EUROPE),
	/** Bahrain */
	BH("bh", "BHR", "Bahrain", Continent.ASIA),
	/** Burundi */
	BI("bi", "BDI", "Burundi", Continent.AFRICA),
	/** Benin */
	BJ("bj", "BEN", "Benin", Continent.AFRICA),
	/** Saint Barth&eacute;lemy */
	BL("bl", "BLM", "Saint Barthélemy", Continent.NORTH_AMERICA),
	/** Bermuda */
	BM("bm", "BMU", "Bermuda", Continent.NORTH_AMERICA),
	/** Brunei */
	BN("bn", "BRN", "Brunei", Continent.ASIA),
	/** Bolivia */
	BO("bo", "BOL", "Bolivia", Continent.SOUTH_AMERICA),
	/** Brazil */
	BR("br", "BRA", "Brazil", Continent.SOUTH_AMERICA),
	/** Bahamas */
	BS("bs", "BHS", "Bahamas", Continent.NORTH_AMERICA),
	/** Bhutan */
	BT("bt", "BTN", "Bhutan", Continent.ASIA),
	/** Bouvet Island */
	BV("bv", "BVT", "Bouvet Island", Continent.ANTARTICTICA),
	/** Botswana */
	BW("bw", "BWA", "Botswana", Continent.AFRICA),
	/** Belarus */
	BY("by", "BLR", "Belarus", Continent.EUROPE),
	/** Belize */
	BZ("bz", "BLZ", "Belize", Continent.NORTH_AMERICA),
	/** Canada */
	CA("ca", "CAN", "Canada", Continent.NORTH_AMERICA),
	/** Cocos Islands */
	CC("cc", "CCK", "Cocos Islands", Continent.ASIA),
	/** The Democratic Republic Of Congo */
	CD("cd", "COD", "The Democratic Republic Of Congo", Continent.AFRICA),
	/** Central African Republic */
	CF("cf", "CAF", "Central African Republic", Continent.AFRICA),
	/** Congo */
	CG("cg", "COG", "Congo", Continent.AFRICA),
	/** Switzerland */
	CH("ch", "CHE", "Switzerland", Continent.EUROPE),
	/** C&ocirc;te d'Ivoire */
	CI("ci", "CIV", "Côte d'Ivoire", Continent.AFRICA),
	/** Cook Islands */
	CK("ck", "COK", "Cook Islands", Continent.OCEANIA),
	/** Chile */
	CL("cl", "CHL", "Chile", Continent.SOUTH_AMERICA),
	/** Cameroon */
	CM("cm", "CMR", "Cameroon", Continent.AFRICA),
	/** China */
	CN("cn", "CHN", "China", Continent.ASIA),
	/** Colombia */
	CO("co", "COL", "Colombia", Continent.SOUTH_AMERICA),
	/** Costa Rica */
	CR("cr", "CRI", "Costa Rica", Continent.NORTH_AMERICA),
	/** Serbia and Montenegro */
	CS("cs", "SCG", "Serbia and Montenegro", Continent.EUROPE),
	/** Cuba */
	CU("cu", "CUB", "Cuba", Continent.NORTH_AMERICA),
	/** Cape Verde */
	CV("cv", "CPV", "Cape Verde", Continent.AFRICA),
	/** Christmas Island */
	CX("cx", "CXR", "Christmas Island", Continent.ASIA),
	/** Cyprus */
	CY("cy", "CYP", "Cyprus", Continent.EUROPE),
	/** Czech Republic */
	CZ("cz", "CZE", "Czech Republic", Continent.EUROPE),
	/** Germany */
	DE("de", "DEU", "Germany", Continent.EUROPE),
	/** Djibouti */
	DJ("dj", "DJI", "Djibouti", Continent.AFRICA),
	/** Denmark */
	DK("dk", "DNK", "Denmark", Continent.EUROPE),
	/** Dominica */
	DM("dm", "DMA", "Dominica", Continent.NORTH_AMERICA),
	/** Dominican Republic */
	DO("do", "DOM", "Dominican Republic", Continent.NORTH_AMERICA),
	/** Algeria */
	DZ("dz", "DZA", "Algeria", Continent.AFRICA),
	/** Ecuador */
	EC("ec", "ECU", "Ecuador", Continent.SOUTH_AMERICA),
	/** Estonia */
	EE("ee", "EST", "Estonia", Continent.EUROPE),
	/** Egypt */
	EG("eg", "EGY", "Egypt", Continent.AFRICA),
	/** Western Sahara */
	EH("eh", "ESH", "Western Sahara", Continent.AFRICA),
	/** Eritrea */
	ER("er", "ERI", "Eritrea", Continent.AFRICA),
	/** Spain */
	ES("es", "ESP", "Spain", Continent.EUROPE),
	/** Ethiopia */
	ET("et", "ETH", "Ethiopia", Continent.AFRICA),
	/**
	 * European Union
	 * 
	 * <strong>NOT STANDARD</strong>
	 */
	EU("eu", "EUR", "European Union", Continent.EUROPE),
	/** Finland */
	FI("fi", "FIN", "Finland", Continent.EUROPE),
	/** Fiji */
	FJ("fj", "FJI", "Fiji", Continent.OCEANIA),
	/** Falkland Islands */
	FK("fk", "FLK", "Falkland Islands", Continent.SOUTH_AMERICA),
	/** Micronesia */
	FM("fm", "FSM", "Micronesia", Continent.OCEANIA),
	/** Faroe Islands */
	FO("fo", "FRO", "Faroe Islands", Continent.EUROPE),
	/** France */
	FR("fr", "FRA", "France", Continent.EUROPE),
	/** Gabon */
	GA("ga", "GAB", "Gabon", Continent.AFRICA),
	/** United Kingdom */
	GB("gb", "GBR", "United Kingdom", Continent.EUROPE),
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
	GD("gd", "GRD", "Grenada", Continent.NORTH_AMERICA),
	/** Georgia */
	GE("ge", "GEO", "Georgia", Continent.ASIA),
	/** French Guiana */
	GF("gf", "GUF", "French Guiana", Continent.SOUTH_AMERICA),
	/** Guernsey */
	GG("gg", "GGY", "Guernsey", Continent.EUROPE),
	/** Ghana */
	GH("gh", "GHA", "Ghana", Continent.AFRICA),
	/** Gibraltar */
	GI("gi", "GIB", "Gibraltar", Continent.EUROPE),
	/** Greenland */
	GL("gl", "GRL", "Greenland", Continent.NORTH_AMERICA),
	/** Gambia */
	GM("gm", "GMB", "Gambia", Continent.AFRICA),
	/** Guinea */
	GN("gn", "GIN", "Guinea", Continent.AFRICA),
	/** Guadeloupe */
	GP("gp", "GLP", "Guadeloupe", Continent.NORTH_AMERICA),
	/** Equatorial Guinea */
	GQ("gq", "GNQ", "Equatorial Guinea", Continent.AFRICA),
	/** Greece */
	GR("gr", "GRC", "Greece", Continent.EUROPE),
	/** South Georgia And The South Sandwich Islands */
	GS("gs", "SGS", "South Georgia And The South Sandwich Islands", Continent.ANTARTICTICA),
	/** Guatemala */
	GT("gt", "GTM", "Guatemala", Continent.NORTH_AMERICA),
	/** Guam */
	GU("gu", "GUM", "Guam", Continent.OCEANIA),
	/** Guinea-Bissau */
	GW("gw", "GNB", "Guinea-Bissau", Continent.AFRICA),
	/** Guyana */
	GY("gy", "GUY", "Guyana", Continent.SOUTH_AMERICA),
	/** Hong Kong */
	HK("hk", "HKG", "Hong Kong", Continent.ASIA),
	/** Heard Island And McDonald Islands */
	HM("hm", "HMD", "Heard Island And McDonald Islands", Continent.ANTARTICTICA),
	/** Honduras */
	HN("hn", "HND", "Honduras", Continent.NORTH_AMERICA),
	/** Croatia */
	HR("hr", "HRV", "Croatia", Continent.EUROPE),
	/** Haiti */
	HT("ht", "HTI", "Haiti", Continent.NORTH_AMERICA),
	/** Hungary */
	HU("hu", "HUN", "Hungary", Continent.EUROPE),
	/** Indonesia */
	ID("id", "IDN", "Indonesia", Continent.ASIA),
	/** Ireland */
	IE("ie", "IRL", "Ireland", Continent.EUROPE),
	/** Israel */
	IL("il", "ISR", "Israel", Continent.ASIA),
	/** Isle Of Man */
	IM("im", "IMN", "Isle Of Man", Continent.EUROPE),
	/** India */
	IN("in", "IND", "India", Continent.ASIA),
	/** British Indian Ocean Territory */
	IO("io", "IOT", "British Indian Ocean Territory", Continent.ASIA),
	/** Iraq */
	IQ("iq", "IRQ", "Iraq", Continent.ASIA),
	/** Iran */
	IR("ir", "IRN", "Iran", Continent.ASIA),
	/** Iceland */
	IS("is", "ISL", "Iceland", Continent.EUROPE),
	/** Italy */
	IT("it", "ITA", "Italy", Continent.EUROPE),
	/** Jersey */
	JE("je", "JEY", "Jersey", Continent.EUROPE),
	/** Jamaica */
	JM("jm", "JAM", "Jamaica", Continent.NORTH_AMERICA),
	/** Jordan */
	JO("jo", "JOR", "Jordan", Continent.ASIA),
	/** Japan */
	JP("jp", "JPN", "Japan", Continent.ASIA),
	/** Kenya */
	KE("ke", "KEN", "Kenya", Continent.AFRICA),
	/** Kyrgyzstan */
	KG("kg", "KGZ", "Kyrgyzstan", Continent.ASIA),
	/** Cambodia */
	KH("kh", "KHM", "Cambodia", Continent.ASIA),
	/** Kiribati */
	KI("ki", "KIR", "Kiribati", Continent.OCEANIA),
	/** Comoros */
	KM("km", "COM", "Comoros", Continent.AFRICA),
	/** Saint Kitts And Nevis */
	KN("kn", "KNA", "Saint Kitts And Nevis", Continent.NORTH_AMERICA),
	/** North Korea */
	KP("kp", "PRK", "North Korea", Continent.ASIA),
	/** South Korea */
	KR("kr", "KOR", "South Korea", Continent.ASIA),
	/** Kuwait */
	KW("kw", "KWT", "Kuwait", Continent.ASIA),
	/** Cayman Islands */
	KY("ky", "CYM", "Cayman Islands", Continent.NORTH_AMERICA),
	/** Kazakhstan */
	KZ("kz", "KAZ", "Kazakhstan", Continent.ASIA),
	/** Laos */
	LA("la", "LAO", "Laos", Continent.ASIA),
	/** Lebanon */
	LB("lb", "LBN", "Lebanon", Continent.ASIA),
	/** Saint Lucia */
	LC("lc", "LCA", "Saint Lucia", Continent.NORTH_AMERICA),
	/** Liechtenstein */
	LI("li", "LIE", "Liechtenstein", Continent.EUROPE),
	/** Sri Lanka */
	LK("lk", "LKA", "Sri Lanka", Continent.ASIA),
	/** Liberia */
	LR("lr", "LBR", "Liberia", Continent.AFRICA),
	/** Lesotho */
	LS("ls", "LSO", "Lesotho", Continent.AFRICA),
	/** Lithuania */
	LT("lt", "LTU", "Lithuania", Continent.EUROPE),
	/** Luxembourg */
	LU("lu", "LUX", "Luxembourg", Continent.EUROPE),
	/** Latvia */
	LV("lv", "LVA", "Latvia", Continent.EUROPE),
	/** Libya */
	LY("ly", "LBY", "Libya", Continent.AFRICA),
	/** Morocco */
	MA("ma", "MAR", "Morocco", Continent.AFRICA),
	/** Monaco */
	MC("mc", "MCO", "Monaco", Continent.EUROPE),
	/** Moldova */
	MD("md", "MDA", "Moldova", Continent.EUROPE),
	/** Montenegro */
	ME("me", "MNE", "Montenegro", Continent.EUROPE),
	/** Saint Martin */
	MF("mf", "MAF", "Saint Martin", Continent.NORTH_AMERICA),
	/** Madagascar */
	MG("mg", "MDG", "Madagascar", Continent.AFRICA),
	/** Marshall Islands */
	MH("mh", "MHL", "Marshall Islands", Continent.OCEANIA),
	/** Macedonia */
	MK("mk", "MKD", "Macedonia", Continent.EUROPE),
	/** Mali */
	ML("ml", "MLI", "Mali", Continent.AFRICA),
	/** Myanmar */
	MM("mm", "MMR", "Myanmar", Continent.ASIA),
	/** Mongolia */
	MN("mn", "MNG", "Mongolia", Continent.ASIA),
	/** Macao */
	MO("mo", "MAC", "Macao", Continent.ASIA),
	/** Northern Mariana Islands */
	MP("mp", "MNP", "Northern Mariana Islands", Continent.OCEANIA),
	/** Martinique */
	MQ("mq", "MTQ", "Martinique", Continent.NORTH_AMERICA),
	/** Mauritania */
	MR("mr", "MRT", "Mauritania", Continent.AFRICA),
	/** Montserrat */
	MS("ms", "MSR", "Montserrat", Continent.NORTH_AMERICA),
	/** Malta */
	MT("mt", "MLT", "Malta", Continent.EUROPE),
	/** Mauritius */
	MU("mu", "MUS", "Mauritius", Continent.AFRICA),
	/** Maldives */
	MV("mv", "MDV", "Maldives", Continent.ASIA),
	/** Malawi */
	MW("mw", "MWI", "Malawi", Continent.AFRICA),
	/** Mexico */
	MX("mx", "MEX", "Mexico", Continent.NORTH_AMERICA),
	/** Malaysia */
	MY("my", "MYS", "Malaysia", Continent.ASIA),
	/** Mozambique */
	MZ("mz", "MOZ", "Mozambique", Continent.AFRICA),
	/** Namibia */
	NA("na", "NAM", "Namibia", Continent.AFRICA),
	/** New Caledonia */
	NC("nc", "NCL", "New Caledonia", Continent.OCEANIA),
	/** Niger */
	NE("ne", "NER", "Niger", Continent.AFRICA),
	/** Norfolk Island */
	NF("nf", "NFK", "Norfolk Island", Continent.OCEANIA),
	/** Nigeria */
	NG("ng", "NGA", "Nigeria", Continent.AFRICA),
	/** Nicaragua */
	NI("ni", "NIC", "Nicaragua", Continent.NORTH_AMERICA),
	/** Netherlands */
	NL("nl", "NLD", "Netherlands", Continent.EUROPE),
	/** Norway */
	NO("no", "NOR", "Norway", Continent.EUROPE),
	/** Nepal */
	NP("np", "NPL", "Nepal", Continent.ASIA),
	/** Nauru */
	NR("nr", "NRU", "Nauru", Continent.OCEANIA),
	/** Niue */
	NU("nu", "NIU", "Niue", Continent.OCEANIA),
	/** New Zealand */
	NZ("nz", "NZL", "New Zealand", Continent.OCEANIA),
	/** Oman */
	OM("om", "OMN", "Oman", Continent.ASIA),
	/** Panama */
	PA("pa", "PAN", "Panama", Continent.NORTH_AMERICA),
	/** Peru */
	PE("pe", "PER", "Peru", Continent.SOUTH_AMERICA),
	/** French Polynesia */
	PF("pf", "PYF", "French Polynesia", Continent.OCEANIA),
	/** Papua New Guinea */
	PG("pg", "PNG", "Papua New Guinea", Continent.OCEANIA),
	/** Philippines */
	PH("ph", "PHL", "Philippines", Continent.ASIA),
	/** Pakistan */
	PK("pk", "PAK", "Pakistan", Continent.ASIA),
	/** Poland */
	PL("pl", "POL", "Poland", Continent.EUROPE),
	/** Saint Pierre And Miquelon */
	PM("pm", "SPM", "Saint Pierre And Miquelon", Continent.NORTH_AMERICA),
	/** Pitcairn */
	PN("pn", "PCN", "Pitcairn", Continent.OCEANIA),
	/** Puerto Rico */
	PR("pr", "PRI", "Puerto Rico", Continent.NORTH_AMERICA),
	/** Palestine */
	PS("ps", "PSE", "Palestine", Continent.ASIA),
	/** Portugal */
	PT("pt", "PRT", "Portugal", Continent.EUROPE),
	/** Palau */
	PW("pw", "PLW", "Palau", Continent.OCEANIA),
	/** Paraguay */
	PY("py", "PRY", "Paraguay", Continent.SOUTH_AMERICA),
	/** Qatar */
	QA("qa", "QAT", "Qatar", Continent.ASIA),
	/** Reunion */
	RE("re", "REU", "Reunion", Continent.AFRICA),
	/** Romania */
	RO("ro", "ROU", "Romania", Continent.EUROPE),
	/** Serbia */
	RS("rs", "SRB", "Serbia", Continent.EUROPE),
	/** Russia */
	RU("ru", "RUS", "Russia", Continent.ASIA, "Russian Federation"),
	/** Rwanda */
	RW("rw", "RWA", "Rwanda", Continent.AFRICA),
	/** Saudi Arabia */
	SA("sa", "SAU", "Saudi Arabia", Continent.ASIA),
	/** Solomon Islands */
	SB("sb", "SLB", "Solomon Islands", Continent.OCEANIA),
	/** Seychelles */
	SC("sc", "SYC", "Seychelles", Continent.AFRICA),
	/** Sudan */
	SD("sd", "SDN", "Sudan", Continent.AFRICA),
	/** Sweden */
	SE("se", "SWE", "Sweden", Continent.EUROPE),
	/** Singapore */
	SG("sg", "SGP", "Singapore", Continent.ASIA),
	/** Saint Helena */
	SH("sh", "SHN", "Saint Helena", Continent.AFRICA),
	/** Slovenia */
	SI("si", "SVN", "Slovenia", Continent.EUROPE),
	/** Svalbard And Jan Mayen */
	SJ("sj", "SJM", "Svalbard And Jan Mayen", Continent.EUROPE),
	/** Slovakia */
	SK("sk", "SVK", "Slovakia", Continent.EUROPE),
	/** Sierra Leone */
	SL("sl", "SLE", "Sierra Leone", Continent.AFRICA),
	/** San Marino */
	SM("sm", "SMR", "San Marino", Continent.EUROPE),
	/** Senegal */
	SN("sn", "SEN", "Senegal", Continent.AFRICA),
	/** Somalia */
	SO("so", "SOM", "Somalia", Continent.AFRICA),
	/** Suriname */
	SR("sr", "SUR", "Suriname", Continent.SOUTH_AMERICA),
	/** Sao Tome And Principe */
	ST("st", "STP", "Sao Tome And Principe", Continent.AFRICA),
	/** El Salvador */
	SV("sv", "SLV", "El Salvador", Continent.NORTH_AMERICA),
	/** Syria */
	SY("sy", "SYR", "Syria", Continent.ASIA),
	/** Swaziland */
	SZ("sz", "SWZ", "Swaziland", Continent.AFRICA),
	/** Turks And Caicos Islands */
	TC("tc", "TCA", "Turks And Caicos Islands", Continent.NORTH_AMERICA),
	/** Chad */
	TD("td", "TCD", "Chad", Continent.AFRICA),
	/** French Southern Territories */
	TF("tf", "ATF", "French Southern Territories", Continent.ANTARTICTICA),
	/** Togo */
	TG("tg", "TGO", "Togo", Continent.AFRICA),
	/** Thailand */
	TH("th", "THA", "Thailand", Continent.ASIA),
	/** Tajikistan */
	TJ("tj", "TJK", "Tajikistan", Continent.ASIA),
	/** Tokelau */
	TK("tk", "TKL", "Tokelau", Continent.OCEANIA),
	/** Timor-Leste */
	TL("tl", "TLS", "Timor-Leste", Continent.ASIA),
	/** Turkmenistan */
	TM("tm", "TKM", "Turkmenistan", Continent.ASIA),
	/** Tunisia */
	TN("tn", "TUN", "Tunisia", Continent.AFRICA),
	/** Tonga */
	TO("to", "TON", "Tonga", Continent.OCEANIA),
	/** Turkey */
	TR("tr", "TUR", "Turkey", Continent.ASIA),
	/** Trinidad and Tobago */
	TT("tt", "TTO", "Trinidad and Tobago", Continent.NORTH_AMERICA),
	/** Tuvalu */
	TV("tv", "TUV", "Tuvalu", Continent.OCEANIA),
	/** Taiwan */
	TW("tw", "TWN", "Taiwan", Continent.ASIA),
	/** Tanzania */
	TZ("tz", "TZA", "Tanzania", Continent.AFRICA),
	/** Ukraine */
	UA("ua", "UKR", "Ukraine", Continent.EUROPE),
	/** Uganda */
	UG("ug", "UGA", "Uganda", Continent.AFRICA),
	/** United States Minor Outlying Islands */
	UM("um", "UMI", "United States Minor Outlying Islands", Continent.NORTH_AMERICA),
	/** United States */
	US("us", "USA", "United States", Continent.NORTH_AMERICA),
	/** Uruguay */
	UY("uy", "URY", "Uruguay", Continent.SOUTH_AMERICA),
	/** Uzbekistan */
	UZ("uz", "UZB", "Uzbekistan", Continent.ASIA),
	/** Vatican */
	VA("va", "VAT", "Vatican", Continent.ASIA),
	/** Saint Vincent And The Grenadines */
	VC("vc", "VCT", "Saint Vincent And The Grenadines", Continent.NORTH_AMERICA),
	/** Venezuela */
	VE("ve", "VEN", "Venezuela", Continent.SOUTH_AMERICA),
	/** British Virgin Islands */
	VG("vg", "VGB", "British Virgin Islands", Continent.NORTH_AMERICA),
	/** U.S. Virgin Islands */
	VI("vi", "VIR", "U.S. Virgin Islands", Continent.NORTH_AMERICA),
	/** Vietnam */
	VN("vn", "VNM", "Vietnam", Continent.ASIA, "Viet Nam", "Socialist Republic of Vietnam", "SRV"),
	/** Vanuatu */
	VU("vu", "VUT", "Vanuatu", Continent.OCEANIA),
	/** Wallis And Futuna */
	WF("wf", "WLF", "Wallis And Futuna", Continent.OCEANIA),
	/** Samoa */
	WS("ws", "WSM", "Samoa", Continent.OCEANIA),
	/** Yemen */
	YE("ye", "YEM", "Yemen", Continent.ASIA),
	/** Mayotte */
	YT("yt", "MYT", "Mayotte", Continent.AFRICA),
	/** South Africa */
	ZA("za", "ZAF", "South Africa", Continent.AFRICA),
	/** Zambia */
	ZM("zm", "ZMB", "Zambia", Continent.AFRICA),
	/** Zimbabwe */
	ZW("zw", "ZWE", "Zimbabwe", Continent.AFRICA);

	/**
	 * Finds a PlayerType that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching PlayerType, or null.
	 */
	public static ISOCountry get(final String string) {
		for (final ISOCountry country : values()) {
			if (country.getId().equals(string) || country.getCode().equals(string) || country.name().equals(string) || country.getName().equals(string)) {
				return country;
			}
			if (country.aliases != null) {
				for (String alias : country.aliases) {
					if (alias.equals(string)) {
						return country;
					}
				}
			}
		}
		return null;
	}

	private final String id;

	private final String abbr2;

	private final String abbr3;

	private final String name;

	private final Continent continent;;

	private final ISOCountry parent;

	@Getter
	private final String[] aliases;

	private ISOCountry(final String id, final String abbr3, final String name, final Continent continent, final String... aliases) {
		this.id = id;
		this.abbr2 = id.toUpperCase();
		this.abbr3 = abbr3;
		this.name = name;
		this.parent = null;
		this.aliases = aliases;
		this.continent = continent;
	}

	private ISOCountry(final String id, final String abbr3, final String name, final ISOCountry parent) {
		this.id = id;
		this.abbr2 = id.toUpperCase();
		this.abbr3 = abbr3;
		this.name = name;
		this.parent = parent;
		this.aliases = null;
		this.continent = parent.getContinent();
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

	/**
	 * Returns the continent of this country. If a country is in more than one
	 * continent, returns the one most strongly associated with it,
	 * geographically.
	 * 
	 * @return the continent the country is in.
	 */
	public Continent getContinent() {
		return this.continent;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
