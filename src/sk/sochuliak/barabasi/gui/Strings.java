package sk.sochuliak.barabasi.gui;

public class Strings {

	public static String APPLICATION_NAME = "Giraphe";
	
	// COMMON
	public static String OK = "OK";
	public static String CANCEL = "Zru�i�";
	public static String WRONG_VALUE = "Nespr�vne vyplnen� pole";
	public static String ERROR = "Chyba";
	public static String WARNING = "Upozornenie";
	public static String TO = "k";
	
	public static String CALCULATE = "Prepo��ta�";
	
	public static String LINEAR_REGRESION = "Line�rna regresia";
	
	public static String ALREADY_SHOWN_DISTRIBUTION = "Tieto siete u� maj� zobrazen� distrib�ciu";
	public static String ALREADY_EXISTS_NETWORK = "Nasleduj�ca sie� nebude importovan�, preto�e sa v aplik�cii nach�dza sie� s toto�nym n�zvom:";
	public static String INVALID_NETWORK_IMPORTING = "Nasleduj�ca sie� nebude importovan�, preto�e je nevalidn�:";
	
	// MENU
	public static String MENU_PROGRAM = "Program";
	public static String MENU_NEW_NETWORK = "Vygenerova� nov� sie�";
	public static String MENU_IMPORT = "Import";
	public static String MENU_EXPORT = "Export";
	public static String MENU_CLOSE_PROGRAM = "Zavrie�";
	public static String MENU_ANALYSIS = "Anal�za";
	public static String MENU_ANALYSIS_SHOW_DEGREE_DISTRIBUTION = "Distrib�cia stup�a uzlov";
	public static String MENU_ANALYSIS_SHOW_DEGREE_DISTRIBUTION_LOG = "Distrib�cia stup�a uzlov - Log";
	public static String MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION = "Distrib�cia klasteriza�n�ho koeficientu uzlov";
	public static String MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION_LOG = "Distrib�cia klasteriza�n�ho koeficientu uzlov - Log";
	public static String MENU_INFO = "Info";
	public static String MENU_INFO_ABOUT_SOFTWARE = "O programe";
	public static String MENU_INFO_ABOUT_SOFTWARE_TEXT = "Program vznikol za ��elom generovania a anal�zy siet� vr�mci diplomovej pr�ce Rast siete riaden� klasteriz�ciou.";
	public static String MENU_INFO_ABOUT_AUTHOR = "O autorovi";
	public static String MENU_INFO_ABOUT_AUTHOR_TEXT = "Martin Sochuliak";
	
	// NETWORKS LIST
	public static String NETWORK_LIST_NETWORKS_LIST = "Zoznam siet�";
	public static String NETWORK_LIST_ADD_NETWORK = "Pridaj";
	public static String NETWORK_LIST_SHOW_INFO_BUTTON = "Zobraz Info >";
	public static String NETWORK_LIST_REMOVE_NETWORK = "Odstr��";
	public static String NETWORK_LIST_REMOVE_NETWORK_QUESTION = "Odstr�ni� sie�";
	
	// BASIC PROPERTIES
	public static String BASIC_PROPERTIES_TITLE = "Z�kladn� vlastnosti siete";
	public static String BASIC_PROPERTIES_PROPERTY_NAME = "Vlastnos�";
	public static String BASIC_PROPERTIES_PROPERTY_VALUE = "Hodnota";
	public static String BASIC_PROPERTIES_TOTAL_NODES_COUNT = "Po�et uzlov";
	public static String BASIC_PROPERTIES_AVERAGE_NODE_DEGREE = "Priemern� stupe� uzla";
	public static String BASIC_PROPERTIES_AVERAGE_NODE_CLUSTER = "Priemern� klasteriza�n� koeficient";
	public static String BASIC_PROPERTIES_NUMBER_OF_NEIGHBORING_NODES = "Po�et dvoj�c susedov";
	public static String BASIC_PROPERTIES_AVERAGE_DISTANCE = "Priemern� vzdialenos� uzlov";
	public static String BASIC_PROPERTIES_MAX_NODE_DEGREE = "Najvy��� stupe� uzla";
	
	// NEW NETWORK DIALOG
	public static String NEW_NETWORK_DIALOG_TITLE = "Nov� sie�";
	public static String NEW_NETWORK_DIALOG_NAME = "N�zov siete";
	public static String NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT = "Rast riaden�";
	public static String NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_NODE_DEGREE = "Stup�om uzla";
	public static String NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_NODE_CLASTER = "Klasteriz�ciou";
	public static String NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_RANDOM = "N�hodne";
	public static String NEW_NETWORK_DIALOG_NUMBER_OF_NODES = "Po�et uzlov";
	public static String NEW_NETWORK_DIALOG_NUMBER_OF_EDGES = "Po�et susedov uzla";
	
	public static String NEW_NETWORK_DIALOG_BASIC_INFO = "Z�kladn� inform�cie";
	public static String NEW_NETWORK_DIALOG_ADVANCED_INFO = "Pokro�il� inform�cie";
	public static String NEW_NETWORK_DIALOG_PREDEFINED = "Preddefinovan�";
	public static String NEW_NETWORK_DIALOG_OWN = "Vlastn�";
	public static String NEW_NETWORK_DIALOG_FIRST_EDGE = "Prv� hrana";
	public static String NEW_NETWORK_DIALOG_CONNECTING_WITH_SELECTING_NODE = "Prip�janie k susedom vybran�ho uzla";
	public static String NEW_NETWORK_DIALOG_EDGES = "Hr�n";
	public static String NEW_NETWORK_DIALOG_RANGE_NEIGHBOR = "Susedom";
	public static String NEW_NETWORK_DIALOG_RANGE_ALL = "Celej sieti";
	public static String NEW_NETWORK_DIALOG_ADD = "Prida�";
	public static String NEW_NETWORK_DIALOG_REMOVE = "Odobra�";
	
	// NEW NETWORK PROGRESS BAR
	public static String NEW_NETWORK_PROGRESS_BAR_TITLE = "Generujem sie�";
	public static String NEW_NETWORK_PROGRESS_BAR_LABEL = "Generujem sie�";
	
	// DEGREE DISTRIBUTION
	public static String DEGREE_DISTRIBUTION_TITLE = "Distrib�cia stup�a uzlov";
	public static String DEGREE_DISTRIBUTION_X_AXIS_LABEL = "k";
	public static String DEGREE_DISTRIBUTION_Y_AXIS_LABEL = "Normovan� # uzlov stup�a k";
	
	public static String DEGREE_DISTRIBUTION_LOG_TITLE = "Distrib�cia stup�a uzlov - Log";
	
	// CLUSTER DISTRIBUTION
	public static String CLUSTER_DISTRIBUTION_TITLE = "Distrib�cia klasteriza�n�ho koeficientu uzlov";
	public static String CLUSTER_DISTRIBUTION_X_AXIS_LABEL = "k";
	public static String CLUSTER_DISTRIBUTION_Y_AXIS_LABEL = "C(k)";
	
	public static String CLUSTER_DISTRIBUTION_LOG_TITLE = "Distrib�cia klasteriza�n�ho koeficientu uzlov - Log";
	
	// INFO
	public static String INFO_PANEL_START_POINT = "Za�iato�n� bod";
	public static String INFO_PANEL_END_POINT = "Koncov� bod";
	public static String INFO_PANEL_CALCULATE = "Vypo��ta�";
	public static String INFO_PANEL_RESULT = "�k�lovac� exponent";
	
	// NETWORK SELECT DIALOG
	public static String NETWORK_SELECT_DIALOG_TITLE = "Vybra� sie�";
	public static String NETWORK_SELECT_DIALOG_NO_SELECTION = "Nevybrali ste �iadnu sie�!";
}
