var app = angular.module('pithyurl_main', [ 'ngCookies', 'ngResource', 'ngSanitize',
		'ngRoute' ]);

var host = location.hostname+(location.port ? ":" + location.port + "/" : "/");
var mainView = true;

var stateAcronyms = {
		"AE": "United Arab Emirates",
		"AF": "Afghanistan",
		"AL": "Albania",
		"AM": "Armenia",
		"AO": "Angola",
		"AR": "Argentina",
		"AT": "Austria",
		"AU": "Australia",
		"AZ": "Azerbaijan",
		"BA": "Bosnia and Herzegovina",
		"BD": "Bangladesh",
		"BE": "Belgium",
		"BF": "Burkina Faso",
		"BG": "Bulgaria",
		"BI": "Burundi",
		"BJ": "Benin",
		"BN": "Brunei Darussalam",
		"BO": "Bolivia",
		"BR": "Brazil",
		"BS": "Bahamas",
		"BT": "Bhutan",
		"BW": "Botswana",
		"BY": "Belarus",
		"BZ": "Belize",
		"CA": "Canada",
		"CD": "Congo, The Democratic Republic of the",
		"CF": "Central African Republic",
		"CG": "Congo",
		"CH": "Switzerland",
		"CI": "Cote d'Ivoire",
		"CL": "Chile",
		"CM": "Cameroon",
		"CN": "China",
		"CO": "Colombia",
		"CR": "Costa Rica",
		"CU": "Cuba",
		"CY": "Cyprus",
		"CZ": "Czech Republic",
		"DE": "Germany",
		"DJ": "Djibouti",
		"DK": "Denmark",
		"DO": "Dominican Republic",
		"DZ": "Algeria",
		"EC": "Ecuador",
		"EE": "Estonia",
		"EG": "Egypt",
		"ER": "Eritrea",
		"ES": "Spain",
		"ET": "Ethiopia",
		"FI": "Finland",
		"FJ": "Fiji",
		"FK": "Falkland Islands (Malvinas)",
		"FR": "France",
		"GA": "Gabon",
		"GB": "United Kingdom",
		"GE": "Georgia",
		"GH": "Ghana",
		"GL": "Greenland",
		"GM": "Gambia",
		"GN": "Guinea",
		"GQ": "Equatorial Guinea",
		"GR": "Greece",
		"GT": "Guatemala",
		"GW": "Guinea-Bissau",
		"GY": "Guyana",
		"HN": "Honduras",
		"HR": "Croatia",
		"HT": "Haiti",
		"HU": "Hungary",
		"ID": "Indonesia",
		"IE": "Ireland",
		"IL": "Israel",
		"IN": "India",
		"IQ": "Iraq",
		"IR": "Iran, Islamic Republic of",
		"IS": "Iceland",
		"IT": "Italy",
		"JM": "Jamaica",
		"JO": "Jordan",
		"JP": "Japan",
		"KE": "Kenya",
		"KG": "Kyrgyzstan",
		"KH": "Cambodia",
		"KP": "Korea, Democratic People's Republic of",
		"KR": "Korea, Republic of",
		"KW": "Kuwait",
		"KZ": "Kazakhstan",
		"LA": "Lao People's Democratic Republic",
		"LB": "Lebanon",
		"LR": "Liberia",
		"LS": "Lesotho",
		"LT": "Lithuania",
		"LU": "Luxembourg",
		"LV": "Latvia",
		"LY": "Libyan Arab Jamahiriya",
		"MA": "Morocco",
		"MD": "Moldova, Republic of",
		"ME": "Montenegro",
		"MG": "Madagascar",
		"MK": "Macedonia",
		"ML": "Mali",
		"MM": "Myanmar",
		"MN": "Mongolia",
		"MR": "Mauritania",
		"MW": "Malawi",
		"MX": "Mexico",
		"MY": "Malaysia",
		"MZ": "Mozambique",
		"NA": "Namibia",
		"NC": "New Caledonia",
		"NE": "Niger",
		"NG": "Nigeria",
		"NI": "Nicaragua",
		"NL": "Netherlands",
		"NO": "Norway",
		"NP": "Nepal",
		"NZ": "New Zealand",
		"OM": "Oman",
		"PA": "Panama",
		"PE": "Peru",
		"PG": "Papua New Guinea",
		"PH": "Philippines",
		"PK": "Pakistan",
		"PL": "Poland",
		"PR": "Puerto Rico",
		"PS": "Palestinian Territory",
		"PT": "Portugal",
		"PY": "Paraguay",
		"QA": "Qatar",
		"RE": "Reunion",
		"RO": "Romania",
		"RS": "Serbia",
		"RU": "Russian Federation",
		"RW": "Rwanda",
		"SA": "Saudi Arabia",
		"SB": "Solomon Islands",
		"SD": "Sudan",
		"SE": "Sweden",
		"SI": "Slovenia",
		"SK": "Slovakia",
		"SL": "Sierra Leone",
		"SN": "Senegal",
		"SO": "Somalia",
		"SR": "Suriname",
		"SS": "South Sudan",
		"SV": "El Salvador",
		"SY": "Syrian Arab Republic",
		"SZ": "Swaziland",
		"TD": "Chad",
		"TF": "French Southern Territories",
		"TG": "Togo",
		"TH": "Thailand",
		"TJ": "Tajikistan",
		"TL": "Timor-Leste",
		"TM": "Turkmenistan",
		"TN": "Tunisia",
		"TR": "Turkey",
		"TT": "Trinidad and Tobago",
		"TW": "Taiwan",
		"TZ": "Tanzania, United Republic of",
		"UA": "Ukraine",
		"UG": "Uganda",
		"US": "United States",
		"UY": "Uruguay",
		"UZ": "Uzbekistan",
		"VE": "Venezuela",
		"VN": "Vietnam",
		"VU": "Vanuatu",
		"WS": "Samoa",
		"YE": "Yemen",
		"ZA": "South Africa",
		"ZM": "Zambia",
		"ZW": "Zimbabwe",
	}

