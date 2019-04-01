package eu.jvx.saac;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.browser.Window;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.KeyboardEvent;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.xml.NodeList;
import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.DataArray;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.getBy.GetBy2;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.interfaces.simple.publish.SimplePublish3;
import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.parse.ParsePrimitive;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.lib.JvxClientException;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.ClassList;
import eu.jvx.js.ui.Autocomplete;
import eu.jvx.js.ui.Autocomplete.AutocompleteSettings;
import eu.jvx.saac.SaacEditor.SaacConfig;
import eu.jvx.saac.SaacEditor.SaacMenuVisiblity;
import eu.jvx.saac.SaacEditor.SaacRootType;

import static eu.jvx.js.lib.bindings.VanillaTools.*;


public class SaacEditor
{
	protected String name;
	protected final HTMLElement target;
	protected final SaacConfig config;
	protected final SaacApi rpc;
	
	public SaacEditor(HTMLElement target, SaacConfig config)
	{
		name = String.valueOf(Integer.toHexString(System.identityHashCode(this)));
		this.target = target;
		this.config = config;
		this.rpc = config.api;
	}
	
	/**************************** Common functions ********************************/
	public static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("SaacEditor"));
	
	public static final String ROOT_NAMESPACE = "fixture_namespace";
	public static final String ROOT_FIXTURE_FUNCTION_NAME = "fixture_function";
	public static final String ROOT_FIXTURE_FUNCTION_INDEX = "fixture_argument_index";
	
	static
	{
		VanillaTools.addCssRules
		(
			/**************************** General style ***********************************/
			".saac saac_primary_container > .saac saac_function_container > .saac_varadic_remove {display:none}",
			"textarea.saac {tab-size: 4;}",
			".saac {display: inline-block;transition: all 0.3s cubic-bezier(.25,.8,.25,1);vertical-align: top;}",
			"button.glyphicon { border:none; background-color: white; margin: 3px;}",
			".saac.saac_no_show {display:none !important;}",
			".saac.saac_hidden_console_content {display:block;}",

			/***************************** Menu styles ************************************/
			".saac_menubar {display: block;font-size: 25px;margin-bottom: 15px;}",
			".saac_menubar > *,.saac_argument_menu > * {padding: 10px;box-shadow: 0 3px 5px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);}",
			".saac_menubar > *:hover,.saac_argument_menu > *:hover {box-shadow: 0 7px 15px rgba(0,0,0,0.25), 0 7px 16px rgba(0,0,0,0.22);}",
			".saac.glyphicon.saac_disabled {background-color: grey;color: white;}",


			/************************** clipboard tricks **********************************/
			".saac_operation_insert_from_clipboard {position: relative;}",
			".saac_operation_insert_from_clipboard textarea {position: absolute;top: 0;right: 0;margin: 0;padding: 0;cursor: pointer;opacity: 0;width: 100%;height: 100%;}",
			".saac_operation_insert_from_clipboard.active {border: 3px solid blue;padding: 7px;}",

			/******************************* Argument menu ********************************/
			".saac_argument_menu {display:inline-block;margin: 3px;}",
			".saac_argument_menu > * {display:none;margin-bottom: 6px;padding: 3px;font-size: 18px;}",
			".saac_argument_menu > .saac_show_menu {display:inline-block;/*font-size: 10px;*/}",
			".saac_argument_menu:hover > .saac_show_menu {display:none;}",
			".saac_argument_menu:hover > * {/*display:inline-block;*/display:block;}",

			/************************** Varadic add/remove function ***********************/
			".saac_varadic_remove,.saac_varadic_add {display: inline-block;margin-bottom: 6px;padding: 3px;font-size: 18px;align:center;}",

			/* Show varadic add button in the argument container and remove button in the 
					,function container if the type is supports */
			".saac_argument.saac_arg_varadric > .saac_argument_variadic_menu,.saac_argument.saac_arg_varadric > .saac_arg_container> .saac_function_container > .saac_varadic_remove {display: block;}",
			".saac_argument.saac_arg_varadric > .saac_arg_container> .saac_function_container > .saac_varadic_remove,.saac_argument_variadic_menu,.saac_varadic_remove {display: inline-block;vertical-align: center;}",

			/**************************** function container ******************************/
			".saac_menubar,.saac_function_container > * {vertical-align: top;}",
			".saac_function_autocomplete {margin-left:20px;}",
			".saac_function_description {display: block;color: rgba(0, 67, 255, 0.72);}",
			".saac_function_container {box-shadow: 0 3px 5px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);display: block;}",
			".saac_arg_container {display: inline-block;}",
			".saac_f_arguments {display: block;padding: 5px;}",
			".saac_argument {display: block;box-shadow: 0 3px 5px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);margin-left: 5px;margin-top: 5px;}",
			".saac_function_container.saac_viewmode_horizontal> .saac_f_arguments> .saac_argument {display:inline-block;}",


			/******************************* Color profile ********************************/
			".saac_function_ok_assembly {background-color: lightgreen;}",
			".saac_function_ok_runtime {background-color: lightyellow;}",
			".saac_function_fail {background-color: lightred;}",
			".saac_f_ret_type,.saac_arg_type {font-size: 12px;color: #C27331;display: inline-block;}",
			".saac_arg_name {font-size: 13px;color:grey;}",
			".saac_arg_descr {color: #5A5A5A;}",
			".saac_function_autocomplete {color:#000000;font-weight: bold;font-size:16px;}",
			".saac.saac_arg_descr,.saac.saac_arg_name,.saac.saac_f_ret_type {vertical-align: sub;}",
			".saac_f_arguments {display: inline-block}",
			
			/******************************* Overview mode ********************************/
			".saac_overview_mode .saac_function_description,.saac_overview_mode .saac_show_menu {display:none;}",
			".saac_overview_mode .saac_f_arguments:hover .saac_function_description,.saac_overview_mode .saac_f_arguments:hover .saac_show_menu {display:inline-block;}",
			
			/******************************* Minimal view ********************************/
			
			".saac-minimal .saac_f_ret_type, .saac-minimal .saac_arg_descr, .saac-minimal .saac_view_arguments_mode, .saac-minimal .saac_argument_menu, .saac-minimal .saac_menubar {display:none}",
			".saac-minimal .saac_varadic_remove {display:inline-block;}",
			".saac-minimal .saac_function_autocomplete {margin-left: 5px}"
		);
	}
	
	//
	// Template related functions
	//
	protected static String renderTemplate(String name)
	{
		if("saac/argument".equals(name))
		{
			return 	"		<div class=\"saac saac_argument\">\n" + 
					"			<!-- TODO Toolbar -->\n" + 
					"			<div class=\"saac saac_arg_type\"></div> <div class=\"saac saac_arg_name\"></div>:<div class=\"saac saac_arg_descr\"></div>\n" + 
					"			\n" + 
					"			\n" + 
					"			<div class=\"saac saac_argument_menu\">\n" + 
					"				<div class=\"saac saac_show_menu glyphicon glyphicon-eye-open\"></div>\n" + 
					"				<div class=\"saac saac_operation_remove glyphicon glyphicon-remove-circle\" data-toggle=\"tooltip\" title=\"Eljárás törlése\">\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_clipboard_cut glyphicon glyphicon-scissors\" data-toggle=\"tooltip\" title=\"Eljárás kivágása\">\n" + 
					"				</div>\n" + 
					"		\n" + 
					"				<div class=\"saac saac_operation_insert_from_clipboard glyphicon glyphicon-paste\" data-toggle=\"tooltip\" title=\"Eljárás beillesztése\">\n" + 
					"					<textarea class=\"saac saac_clipboard_insert_area\"></textarea>\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_all_clipboard glyphicon glyphicon-copy\" data-toggle=\"tooltip\" title=\"Eljárás másolása\">\n" + 
					"				</div>\n" + 
					"			</div>\n" + 
					"		\n" + 
					"			<div class=\"saac saac_arg_container\">\n" + 
					"				\n" + 
					"			</div>\n" + 
					"		\n" + 
					"			<div class=\"saac saac_argument_variadic_menu\">\n" + 
					"				<button class=\"saac saac_varadic_add glyphicon glyphicon-plus-sign\" data-toggle=\"tooltip\" title=\"Paraméter hozzáadása\"></button>\n" + 
					"			\n" + 
					"			</div>\n" + 
					"		</div>";
		}
		else if("saac/function_container".equals(name))
		{
			return 	"<div class=\"saac saac_function_container\">\n" + 
					"			<div class=\"saac saac_function_description\"></div>\n" + 
					"			<button class=\"saac saac_varadic_remove glyphicon glyphicon-minus-sign\"></button>\n" + 
					"			<!-- \n" + 
					"				Autocomplete-nél:\n" + 
					"					kell a container szülőjének a tipusa\n" + 
					"			\n" + 
					"			A funkciók 3 szintje:\n" + 
					"				- \"generálás futás időben\": SimpleGet<GetBy1<?, ?>> \n" + 
					"				- Elsőrendű függvények: GetBy<Return, Input>\n" + 
					"				- util függvények: concat(\"valami: \", 12)\n" + 
					"			 -->\n" + 
					"			\n" + 
					"			<input type=\"hidden\" name=\"w\" class=\"saac f_wrapper\"/>\n" + 
					"			\n" + 
					"			\n" + 
					"			<input type=\"hidden\" name=\"f\" class=\"saac saac_f_function_id\"/>\n" + 
					"			\n" + 
					"			<div class=\"saac saac_f_ret_type\"></div><br/>\n" + 
					"			\n" + 
					"			<input type=\"text\" class=\"saac saac_function_autocomplete autocomplete\" data-originsize=\"5\" size=\"5\" placeholder=\"\"/>\n" + 
					"			<button class=\"saac saac_view_arguments_mode glyphicon glyphicon-resize-vertical\"></button>\n" + 
					"			\n" + 
					"			<div class=\"saac saac_f_arguments\">\n" + 
					"				<!-- entries -->\n" + 
					"			</div>\n" + 
					"		</div>";
		}
		else if("saac/main".equals(name))
		{
			return 	"<div class=\"saac saac-minimal saac_entry_point\">\n" + 
					"			<div class=\"saac saac_menubar\">\n" + 
					"				<div class=\"saac saac_operation_remove saac_top_level_opearation glyphicon glyphicon-remove-circle\" data-toggle=\"tooltip\" title=\"Eljárás törlése\">\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_clipboard_cut saac_top_level_opearation glyphicon glyphicon-scissors\" data-toggle=\"tooltip\" title=\"Eljárás kivágása\">\n" + 
					"				</div>\n" + 
					"		\n" + 
					"				<div class=\"saac saac_operation_insert_from_clipboard saac_top_level_opearation glyphicon glyphicon-paste\" data-toggle=\"tooltip\" title=\"Eljárás beillesztése\">\n" + 
					"					<textarea class=\"saac saac_clipboard_insert_area\"></textarea>\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_all_clipboard saac_top_level_opearation glyphicon glyphicon-copy\" data-toggle=\"tooltip\" title=\"Eljárás másolása\">\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_save saac_top_level_opearation glyphicon glyphicon-floppy-disk\" data-toggle=\"tooltip\" title=\"Eljárás mentése lemezre\">\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_execute saac_top_level_opearation glyphicon glyphicon-triangle-right\" data-toggle=\"tooltip\" title=\"Eljárás futtatása\">\n" + 
					"				</div>\n" + 
					"				\n" + 
					"				<div class=\"saac saac_operation_show_console saac_top_level_opearation glyphicon glyphicon-console\" data-toggle=\"tooltip\" title=\"Konzol megnyitása\">\n" + 
					"				</div>\n" + 
					"			</div>\n" + 
					"			\n" + 
					"			<div class=\"saac saac_project_file_container\">\n" + 
					"				\n" + 
					"			</div>\n" + 
					"			\n" + 
					"			<div class=\"saac saac_primary_container\">\n" + 
					"				<div class=\"saac saac_no_show saac_root_accept_type\"></div>\n" + 
					"				<div class=\"saac saac_arg_type saac_root_type_name\"></div>\n" + 
					"			</div>\n" + 
					"			\n" + 
					"			\n" + 
					"			<div class=\"saac saac_no_show saac_hidden_console_content\">\n" + 
					"				<div class=\"saac saac_console_root\">\n" + 
					"					<pre class=\"saac saac_log_container\">\n" + 
					"					\n" + 
					"					</pre>\n" + 
					"				</div>\n" + 
					"			</div>\n" + 
					"			\n" + 
					"		</div>";
		}
		else if("saac/type".equals(name))
		{
			return "<div class=\"saac_template_type\"></div>";
		}
		else
		{
			return "template doesn't exists: "+name;
		}
	}

	/**
	 * type: info, warning, errorsaac_initEntryPoint
	 * */
	protected void notify_user(String type, String text)
	{
		if(null != config.customNotify)
		{
			config.customNotify.publish(type, text);
		}
		else
		{
			Window.alert(text);
		}
	}

/********************** Typing system related functions ***********************/
	
	public static class SaacType
	{
		public SaacType genericComponentType;
		
	}
	
	public static String shorten_type(String str)
	{
		str = StringTools.getSubstringAfterLastString(str, ".");
		str = str.replaceAll(";", "[]");
		return str;
	}

	public static String saac_type_get_typeclass(DataObject t)
	{
		if(t.has("genericComponentType"))
		{
			return saac_type_get_typeclass(t.getObject("genericComponentType"));
		}
		
		if(t.has("class"))
		{
			return "primitive";
		};
		
		if(t.has("rawType"))
		{
			String  cls = shorten_type(t.getObject("rawType").getString("class"));
			
			boolean subj_func =
					cls.startsWith("GetBy")
				||
					"SimpleGet".equals(cls);
			
			boolean func_no_ret = cls.startsWith("SimplePublish") || "SimpleCall".equals(cls);
			
			if(subj_func  || func_no_ret)
			{
				return "function";
			}
		}
		else
		{
			return "compaund";
		}
		
		return "unknown";
	}

	public static String saac_type_render(DataObject t)
	{
		if(t.has("rawType"))
		{
			String ret = "";
			String cls = shorten_type(t.getObject("rawType").getString("class"));
			
			boolean subj_func =
					cls.startsWith("GetBy")
				||
					"SimpleGet".equals(cls);
			
			boolean func_no_ret = cls.startsWith("SimplePublish") || "SimpleCall".equals(cls);
			
			subj_func |= func_no_ret;
			
			int off = 0;
			
			if(subj_func)
			{
				if(func_no_ret)
				{
					ret += "void <- (";
					off = 0;
				}
				else
				{
					ret += saac_type_render(t.getArray("actualTypeArguments").getObject(0));
					ret += " <- (";
					off = 1;
				}
			}
			else
			{
				ret += cls;
				ret += "<";
			}
			
			DataArray ata = t.getArray("actualTypeArguments");
			for(int i=off;i<ata.size();++i)
			{
				if(i != off)
				{
					ret += ", ";
				}
				ret += saac_type_render(ata.getObject(i));
			}
			
			ret += subj_func?")":">";
			return ret;
		}
		
		if(t.has("genericComponentType"))
		{
			return saac_type_render(t.getObject("genericComponentType"))+"[]";
		}
		
		if(t.has("class"))
		{
			return shorten_type(t.getString("class"));
		}
		
		if(t.has("genericDeclaration"))
		{
			return "<"+t.getString("name")+">";
		}
		
		LoggingTools.tryLogFormat(LOG, LogLevel.ERROR, "unknown type ??: %s", t);
		
		return "??";
	}
	
	public static boolean saac_type_is_variadic(DataObject type)
	{
		if(type.has("genericComponentType"))
		{
			return true;
		}
		
		if(saac_type_render(type).endsWith("[]"))
		{
			return true;
		}
		
		
		LoggingTools.tryLogFormat(LOG, LogLevel.TRACE, "is_variadic? %s %s", saac_type_render(type), type);
		
		return false;
	}

	public static boolean saac_is_function_descriptor(DataObject item)
	{
		return null != item
				&& item.has("id")
				&& item.has("name")
				&& item.has("returning")
				&& item.has("arguments"); 
	}

	public static void saac_validate_arguments(HTMLElement container)
	{
	
	}

	public static void saac_assert_valid_restore_data(DataObject data)
	{
		
	}
	
	public void saac_restore_function(DataObject data)
	{
		saac_restore_function(target, data);
	}

	public void saac_restore_function(HTMLElement container, DataObject data)
	{
		LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "Restoring public static void into container: %s %s", VanillaTools.getDomPath(container), data);
		//const value
		HTMLElement f_ac = container.querySelector(".saac_function_autocomplete");
		
		String it = data.optString("it");
		
		if("ta".equals(it))
		{
			f_ac = alterTag(f_ac, "textarea");
		}
		
		setContent(f_ac, data.optString("content"));
		
		if("ta".equals(it))
		{
			update_textarea_size(container.querySelector(".saac_function_autocomplete"));
		}
		
		saac_update_function_ac_input(f_ac);
		
		boolean pa = data.optBoolean("pa");
		
		if(pa)
		{
			container.querySelector(".saac_view_arguments_mode").click();
		}
		
		String id = data.optString("id");
		
		if(!"".equals(id))
		{
			DataObject func = saac_get_function_by_id(id);
			if(null == func)
			{
				throw new RuntimeException("Invalid function id: "+id);
			}
			
			saac_function_container_set_function(container, func);
	
			//restore args/varargs
			if(data.has("args"))
			{
				saac_restore_function_arguments(container, data.getArray("args"));
			}
		}
	}

	public void saac_restore_function_arguments(HTMLElement container, DataArray args)
	{
		saac_ensure_arguments_container_function_container_count
		(
			container,
			args.size()
		);
		
		//ac is an array of .saac_argument that may be .saac_arg_varadric
		NodeList<HTMLElement> ac = (NodeList<HTMLElement>) (Object) getChildren(container.querySelector(".saac_f_arguments"));
		
		LoggingTools.tryLogFormat(LOG, LogLevel.TRACE, "Restoring arguments %s %s", ac, args);
		
		for(int i=0;i<ac.getLength();++i)
		{
			//i-th html elem
			HTMLElement subj = ac.get(i);
			Object elem = args.get(i);
			
			LoggingTools.tryLogFormat(LOG, LogLevel.TRACE, "Restoring argument parameter %d, %s", i, subj, elem);
			
			if(elem instanceof DataArray)
			{
				DataArray vs = (DataArray) elem;
			
				for(int n = 1;n<vs.size();++n)
				{
					saac_add_varadic(subj);
				}
				
				//getting the i-th varadic parameter's containier (it's a public static void container)
				NodeList<HTMLElement> target = (NodeList<HTMLElement>) (Object) getChildren(subj.querySelector(".saac_arg_container"));
				
				for(int n = 0;n<vs.size();++n)
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.TRACE, "restore varadic to container: %s %s %s %s", target.get(n), vs.getObject(n), "in:", subj);
					
					saac_restore_function
					(
						target.get(n),
						vs.getObject(n)
					);
				}
			}
			else if(elem instanceof DataObject)
			{
				DataObject o = (DataObject) elem;
				saac_restore_function
				(
					saac_find_container_dir_child(subj),
					o
				);
			}
		}
	}
	
/*********************** Dom navigation/modifier utils ************************/
	
	public static HTMLElement saac_get_top_container(HTMLElement container)
	{
		return whereParentWithBoundary(container, ".saac_entry_point", ".saac_entry_point");
	}
	
	
	public static boolean saac_is_container_in_use(HTMLElement container)
	{
		return !"".equals(((HTMLInputElement)container.querySelector(".saac_f_function_id")).getValue());
	}
	
	
	public static void saac_set_function_container_type(HTMLElement container, DataObject type)
	{
		String t = saac_type_get_typeclass(type);
		((HTMLInputElement)container.querySelector(".f_wrapper")).setValue(t);
		((HTMLInputElement)container.querySelector(".saac_function_autocomplete")).setAttribute("placeholder", t);
	}
	
	public void saac_function_container_set_function(HTMLElement container, DataObject func)
	{
		//saac_set_function_container_type(container, func.returning);
		String func_id = "";
		String wrapper_type = "";
		String ret_type = "";
		String func_descr = "";
		
		if(null != func)
		{
			func_id = func.optString("id");
			ret_type = saac_type_render(func.getObject("returning").getObject("type"));
			func_descr = func.optString("descr");
		}
		else
		{
			((HTMLInputElement)container.querySelector(".saac_function_autocomplete")).setValue("");
		}
		
		((HTMLInputElement)container.querySelector(".saac_f_function_id")).setValue(func_id);
		((HTMLInputElement)container.querySelector(".f_wrapper")).setValue(wrapper_type);
		
		setInnerText(container.querySelector(".saac_f_ret_type"), ret_type);
		setInnerText(container.querySelector(".saac_function_description"), func_descr);
		
		saac_ensure_argument_containers(container, null == func? null: func.getArray("arguments"));
	}
	
	public static void saac_update_arg_infos
	(
		HTMLElement arg_container,
		DataObject arg_info
	)
	{
		//primitive
		//function
		
		HTMLElement pcont = arg_container.querySelector(".saac_function_container");
		if(!saac_is_container_in_use(pcont))
		{
			saac_set_function_container_type(pcont, arg_info.getObject("type"));
		}
		
		if(saac_type_is_variadic(arg_info.getObject("type")))
		{
			getClassList(arg_container).add("saac_arg_varadric");
		}
		else
		{
			getClassList(arg_container).remove("saac_arg_varadric");
		}
		
		setInnerText(arg_container.querySelector(".saac_arg_type"), saac_type_render(arg_info.getObject("type")));
		setInnerText(arg_container.querySelector(".saac_arg_name"), arg_info.getString("name"));
		setInnerText(arg_container.querySelector(".saac_arg_descr"), arg_info.getString("description"));
	}
	
	public void saac_ensure_arguments_container_function_container_count
	(
		HTMLElement container,
		int creq
	)
	{
		HTMLElement tar_args = container.querySelector(".saac_f_arguments");
		int cnums = getChildElementCount(tar_args);
		
		//add new one argument containers if needed
		if(creq > cnums)
		{
			for(;creq > cnums;cnums++)
			{
				HTMLElement html = parseHtml(renderTemplate("saac/argument"));
				HTMLElement func = parseHtml(renderTemplate("saac/function_container"));
				html.querySelector(".saac_arg_container").appendChild(func);
				tar_args.appendChild(html);
			}
		}
		
		//delete plus containers (as much we can)
		if(creq < cnums)
		{
			NodeList<HTMLElement> children = (NodeList<HTMLElement>)(JSObject) getChildren(tar_args);
			
			for(int i=cnums-1;i >= creq;--i)
			{
				if(!saac_is_container_in_use(children.get(i).querySelector(".saac_arg_container")))
				{
					remove(children.get(i));
				}
			}
		}
	}
	

	/**
	 * Ensures the argument container exists:
	 * 	- creates the new ones for the requised argument, but keep the sub conatiner.
	 * 	- deletes the extra argument containers at the end (if empty)
	 * 	- creates/remotes add/delete button(as a container) if type is variadic (or array).
	 * 	=> validates conatiner
	 * */
	public void saac_ensure_argument_containers
	(
		HTMLElement container,
		DataArray args
	)
	{
		HTMLElement tar_args = container.querySelector(".saac_f_arguments");
		int creq = null == args?0:args.size();
		
		saac_ensure_arguments_container_function_container_count
		(
			container,
			creq
		);
		
		//update paramater infos (type name descr)
		
		JSArray<HTMLElement> tarc = getChildren(tar_args);
		for(int i=0;i<creq;++i)
		{
			saac_update_arg_infos(tarc.get(i), args.getObject(i));
		}
		
		saac_validate_arguments(container);
	}
	
	public static HTMLElement saac_find_container_dir_parent(HTMLElement elem)
	{
		return whereParentWithBoundary(elem, ".saac_function_container", ".saac_entry_point");
	}
	
	public static HTMLElement saac_find_container_dir_child(HTMLElement elem)
	{
		return elem.querySelector(".saac_function_container");
	}
	
	public static JSArray<HTMLElement> saac_find_container_dir_child_all(HTMLElement elem)
	{
		HTMLElement chk = elem.querySelector(".saac_function_container");
		if(null != chk)
		{
			JSArray<HTMLElement> cs = getChildren((HTMLElement) chk.getParentNode());
			/*if(1 == cs.length)
			{
				return chk;
			}
			else if(1 < cs.length)
			{*/
				return cs;
			//}
		}
		return null;
	}
	
	/**
	 * Selects the nearest descendant .saac_f_arguments and returns
	 * 	with the .saac_argument elements
	 * */
	public static JSArray<HTMLElement> saac_find_container_arguments(HTMLElement container)/*: array[Html]*/
	{
		HTMLElement args = container.querySelector(".saac_f_arguments");
		JSArray<HTMLElement> ret = JSArray.create();
		JSArray<HTMLElement> c = getChildren(args);
		for(int i=0;i< c.getLength();++i)
		{
			HTMLElement s = c.get(i);
			if(is(s, ".saac_argument"))
			{
				ret.push(s);
			}
		}
		
		return ret;
	}
	
	public void saac_add_varadic(HTMLElement container)
	{
		HTMLElement ac =
			//container;
			container.querySelector(".saac_arg_container");
		
		HTMLElement func = parseHtml(renderTemplate("saac/function_container"));
		ac.appendChild(func);	
	}
	
	public void wipe_conainer(HTMLElement container)
	{
		//remove public static void params
		JSArray<HTMLElement> c = saac_find_container_arguments(container);
		for(int i=0;i<c.getLength();++i)
		{
			remove(c.get(i));
		}
		saac_function_container_set_function(container, null);
	}
	
	protected List<DataObject> saacFunctions;

	public DataObject saac_get_function_by_id(String id)
	{
		if(LOG.mayLog(LogLevel.DEBUG))
		{
			NativeJsSupport.getSupport().log(saacFunctions);
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "saac_get_function_by_id(id: %s)", id);
		}
		
		for(DataObject o:saacFunctions)
		{
			if(id.equals(o.getString("id")))
			{
				return o;
			}
		}
		
		return null;
	}
	
	public DataObject saac_get_function_by_name(String name)
	{
		for(DataObject o:saacFunctions)
		{
			if(name.equals(o.getString("name")))
			{
				return o;
			}
		}
		return null;
	}
	
	public static int saac_determine_nth_arg_iam(HTMLElement container)
	{
		HTMLElement ac = whereParentWithBoundary(container, ".saac_argument", ".saac_entry_point");
		if(null == ac)
		{
			return -1;
		}
		
		return index(ac);
	}
	
	public DataObject saac_container_get_function(HTMLElement container)
	{
		String id = ((HTMLInputElement)container.querySelector(".saac_f_function_id")).getValue();
		return saac_get_function_by_id(id);
	}
	
	
	/********************** Communication/serialization utils *********************/
	
	public static boolean __is_opt_set(int subject, int test)
	{
		return (subject & test) == test;
	}
	
	public static enum SaacSerializationMode
	{
		NONE,
		DIRECT,
		ALL
		;
	}
	
	public static DataObject saac_serialize_container
	(
		HTMLElement container,
		SaacSerializationMode parent_mode,
		SaacSerializationMode child_mode
	)
	{
		if(null == container)
		{
			return null;
		}
		
		DataObject ret = new DataObjectTeaVMImpl();
		
		ret.putString("id", ((HTMLInputElement)container.querySelector(".saac_f_function_id")).getValue());
		ret.putString("content",((HTMLInputElement)container.querySelector(".autocomplete")).getValue());
		ret.putString("it", "TEXTAREA".equals(container.querySelector(".autocomplete").getNodeName())?"ta":null);
		ret.putBoolean("pa", getClassList(container.querySelector(".saac_view_arguments_mode")).contains("glyphicon-resize-horizontal"));
		
		if(SaacSerializationMode.NONE != parent_mode)
		{
			ret.putObject
			(
				"parent",
				saac_serialize_container
				(
					saac_find_container_dir_parent(container),
					parent_mode == SaacSerializationMode.ALL?SaacSerializationMode.ALL:SaacSerializationMode.NONE,
					SaacSerializationMode.NONE
				)
			);
		}
		
		if(SaacSerializationMode.NONE != child_mode)
		{
			DataArray c = ret.newArrayInstance();
			JSArray<HTMLElement> chps = saac_find_container_arguments(container);
			
			for(int n=0;n<chps.getLength();++n)
			{
				HTMLElement argcont = chps.get(n);
				if(is(argcont, ".saac_arg_varadric"))
				{
					JSArray<HTMLElement> all = saac_find_container_dir_child_all(argcont);
					DataArray add = ret.newArrayInstance();
					if(null != all)
					{
						for(int i=0;i<all.getLength();++i)
						{
							add.putObject
							(
								saac_serialize_container
								(
									all.get(i),
									SaacSerializationMode.NONE,
									child_mode == SaacSerializationMode.ALL?SaacSerializationMode.ALL:SaacSerializationMode.NONE
								)
							);
						}
					}
					c.putArray(add);
				}
				else
				{
					c.putObject
					(
						saac_serialize_container
						(
							saac_find_container_dir_child(argcont),
							SaacSerializationMode.NONE,
							child_mode == SaacSerializationMode.ALL?SaacSerializationMode.ALL:SaacSerializationMode.NONE
						)
					);
				}
				
			}
			ret.putArray("args", c);
		}
		
		return ret;
	}
	
	/**************************** Bookeeping functions ****************************/
	
	protected static List<DataObject> extractFunctions(DataArray a)
	{
		ArrayList<DataObject> ret = new ArrayList<>();
		for(int i=0;i<a.size();++i)
		{
			ret.add(a.getObject(i));
		}
		return ret;
	}
	
	public void synch_refresh_function_list()
	{
		DataArray src = rpc.listFunctions();
		saacFunctions = extractFunctions(src);
	}
	
	/*
	public void __saac_refresh_function_list(SimplePublish1<List<DataObject>> onRefreshDone)
	{
		if(null != saacFunctions)
		{
			if(null != onRefreshDone)
			{
				onRefreshDone.publish(saacFunctions);
			}
		}
		else
		{
			rpc.listFunctions
			(
				new SimplePublish2<DataArray, JvxClientException>()
				{
					@Override
					public void publish(DataArray a, JvxClientException b)
					{
						saacFunctions = extractFunctions(a);
						
						if(null != onRefreshDone)
						{
							onRefreshDone.publish(saacFunctions);
						}
					}
				}
			);
		}
	}
	*/
	
	/**
	 * check param status:
	 * 		- green		=> assembly time ensurance
	 * 		- yellow	=> valua availabe in runtime (wrapped for runtime lookup)
	 * 		- red		=> incompatible type
	 * 
	 * TODO
	 */
	public static void saac_update_container_validity(HTMLElement container)
	{
		ClassList cl = getClassList(container);
		cl.remove("saac_function_ok_assembly");
		cl.remove("saac_function_ok_runtime");
		cl.remove("saac_function_fail");
	}
	
/************ The module's autocomplete related handler functions *************/
	
	public void __saac_handle_autocomplete(HTMLElement container, SimplePublish1<List<DataObject>> process)
	{
		String cls = ((HTMLInputElement)container.querySelector(".f_wrapper")).getValue();
		//TODO i have to reshapre this type and offering machanism completely, but now
		//to let is work i'm simply commenting out and always offer something 
		//if("".equals(cls) || "function".equals(cls))
		{
			//TODO internal recommendation from the downloaded list.
			//__saac_refresh_function_list(RPC, process);
			HTMLElement parentFunctionContainer = saac_find_container_dir_parent(container);
			
			DataObject parentFunction = null;
			int nth_arg = -1;
			boolean varadic = false;
			
			if(null != parentFunctionContainer)
			{
				parentFunction = saac_container_get_function(parentFunctionContainer);
				nth_arg = saac_determine_nth_arg_iam(container);
				varadic = getClassList(whereParentWithBoundary(container, ".saac_argument", ".saac_entry_point")).contains("saac_arg_varadric");
			}
			else
			{
				//getting the root type:
				HTMLElement rootType = whereParent(container, ".saac_entry_point").querySelector(".saac_root_accept_type");
				if(null != rootType)
				{
					String tr = getInnerText(rootType);
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "Found root type: %s", tr);
					
					if(null != tr && !"".equals(tr))
					{
						String[] arr = tr.split(":");
						if(null != arr && arr.length > 1)
						{
							parentFunction = saac_get_function_by_id(arr[0].trim());
							nth_arg = Integer.parseInt(arr[1]);
						}
					}
				}
			}
			
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "Offering request via autocomplete: (this: %s) parent function: %s arg:  %s", this, parentFunction, nth_arg);
			
			HTMLInputElement input = (HTMLInputElement) container.querySelector(".saac_function_autocomplete");
			
			if(null == parentFunction || nth_arg < 0)
			{
				process.publish(saacFunctions);
			}
			else
			{
				rpc.offerForType
				(
					new SimplePublish2<DataArray, JvxClientException>()
					{
						@Override
						public void publish(DataArray a, JvxClientException b)
						{
							process.publish(extractFunctions(a));
						}
					},
					null != parentFunction?parentFunction.getString("id"):null,
					nth_arg,
					varadic,
					getContent(input),
					getSelectionStart(input),
					getSelectionEnd(input)
				);
			}
		}
		/*else
		{
			//TODO if type is an enum
		}*/
	}
	
	public void saac_update_function_ac_input(HTMLElement input)
	{
		//unset public static void container
		/*if(!"".equals(((HTMLInputElement)input).getValue()))
		{
			saac_function_container_set_function(saac_find_container_dir_parent(input), null);
		}*/
		
		int min = ParsePrimitive.tryParseInt(input.getAttribute("data-originsize"), 5);
		int tar = ((HTMLInputElement)input).getValue().length() + 1;
		if(tar < min)
		{
			tar = min;
		}
		
		((HTMLInputElement)input).setSize(tar);
	}
	
	public void __saac_bind_autocomplete(HTMLElement elem)
	{
		HTMLElement parent = saac_find_container_dir_parent(elem);
		
		AutocompleteSettings<DataObject> settings = new AutocompleteSettings<DataObject>
		(
			elem,
			new SimplePublish2<String, SimplePublish1<List<DataObject>>>()
			{
				@Override
				public void publish(String a, SimplePublish1<List<DataObject>> b)
				{
					__saac_handle_autocomplete
					(
						parent,
						new SimplePublish1<List<DataObject>>()
						{
							@Override
							public void publish(List<DataObject> a)
							{
								String search = ((HTMLInputElement)elem).getValue().toLowerCase();
								ArrayList<DataObject> arr = new ArrayList<>();
								for(DataObject o:a)
								{
									String s = o.optString("name");
									if(null != s && s.toLowerCase().indexOf(search) > -1)
									{
										arr.add(o);
									}
								}
								
								//TODO order start with, then every other in ABC order
								b.publish(arr);
							}
						}
					);
				}
			},
			new SimplePublish3<Event, String, HTMLElement>()
			{
				@Override
				public void publish(Event a, String b, HTMLElement c)
				{
					if(null == c)
					{
						saac_function_container_set_function(parent, null);
					}
					else
					{
						saac_function_container_set_function(parent, saac_get_function_by_id(c.getAttribute("data-select-id")));
					}
				}
			}
		);
		
		settings.renderItem = new GetBy2<HTMLElement, DataObject, String>()
		{
			@Override
			public HTMLElement getBy(DataObject item, String b)
			{
				return new H("div").attrs("class", "autocomplete-suggestion", "data-select-id", item.getString("id"), "data-val", item.getString("name"), "#text", item.getString("name")).getHtml();
				//return "<div class=\"autocomplete-suggestion\" data-select-id=\""+item.getString("id")+"\" data-val=\""+item.getString("name")+"\"><b>"+item.getString("name")+"</b></div>";
			}
		};
		
		settings.minChars = 0;
		
		new Autocomplete(settings);
		
		elem.listenKeyUp(new EventListener<KeyboardEvent>()
		{
			@Override
			public void handleEvent(KeyboardEvent arg0)
			{
				saac_update_function_ac_input(elem);				
			}
		});
		
		/*$(target).typeahead
		(
			{
				highlight: true,
				minLength: 0,
				limit: 15,
				maxItem: 15,
				displayText: function(item)
				{
					return item.name;
				},
				afterSelect: function(item)
				{
					if(is_valuable(this.currentlySelectedItem))
					{
						this.$element[0].value = this.currentlySelectedItem.name;
					}
				},
				source: function(query, process)
				{
					var container = saac_find_container_dir_parent(target);
					__saac_handle_autcomplete(RPC, container, process);
				},
				updater: function(item)
				{
					if(saac_is_function_descriptor(item))
					{
						saac_function_container_set_function(saac_find_container_dir_parent(target), item);
					}
					this.currentlySelectedItem = item;
					return false;
				}
			}
		)
		.keyup
		(
			function()
			{
				saac_update_function_ac_input(target);
			}
		);*/
	}
	
	public void __saac_add_eventlistener_autocomplete()
	{
		//binding autocomplete for new inputs
		target.addEventListener
		(
			"focus",
			new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event event)
				{
					String wcls = "saac_typeahead_wired";
					
					HTMLElement target = (HTMLElement) event.getTarget();
					
					ClassList cl = getClassList(target);
					
					//prevent handling unwanted events
					if(!cl.contains("saac_function_autocomplete"))
					{
						return;
					}
					
					if(!cl.contains(wcls))
					{
						__saac_bind_autocomplete(target);
						cl.add(wcls);
					}
				}
			},
			true
		);
	}
	
	public static void update_textarea_size(HTMLElement tar)
	{
/*		TODO fs = Window.current().getComputedStyle(tar).fontSize;
		cnt = tar.getContent();
		var lines = cnt.split("\n");
		var max = 0; 
		for(var i= 0;i<lines.length;++i)
		{
			var len = (lines[i].split("\t").length-1)*3+lines[i].length;
			if(len > max)
			{
				max = len;
			}
		}
		
		max /= 2;
		
		tar.style.width = (parseInt(fs)*max)+"px";
		tar.style.height = (tar.scrollHeight)+"px";*/
	}
	
	
	public void __saac_add_eventlistener_textarea()
	{
		target.addEventListener
		(
			"keyup",
			new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event e)
				{
					HTMLElement tar = e.getTarget().cast();
					if("TEXTAREA" != tar.getNodeName() || !getClassList(tar).contains("saac"))
					{
						return;
					}
					
					update_textarea_size(tar);
				}
			}
		);
	}
	
	public void __saac_add_eventlistener_argument_sortable()
	{
		//sortable parameters
	}

	/***************** The module's click related handler functions ***************/ 
	
	public void __do_copy_clipboard(HTMLElement target, boolean notify)
	{
		HTMLElement main = whereParentWithBoundary(target, ".saac_entry_point, .saac_argument", ".saac_entry_point");
		if(null != main)
		{
			HTMLElement container = saac_find_container_dir_child(main);
			if(null != container)
			{
				DataObject ser = saac_serialize_container
				(
					container,
					SaacSerializationMode.ALL,
					SaacSerializationMode.ALL
				);
				
				copyToClipboard(JSON.stringify((JSObject)ser.getImpl()));
				
				if(notify)
				{
					notify_user("info", "Másolva a vágólapra");
				}
			}
		}
	}
	
	public void __do_wipe_container(HTMLElement target, boolean notify)
	{
		HTMLElement main = whereParentWithBoundary(target, ".saac_entry_point, .saac_argument", ".saac_entry_point");
		if(null != main)
		{
			HTMLElement container = saac_find_container_dir_child(main);
			if(null != container)
			{
				wipe_conainer(container);
				if(notify)
				{
					notify_user("info", "Eljárás eltávolítva");
				}
			}
		}
	}
	
	public void __do_cut_clipboard(HTMLElement target)
	{
		__do_copy_clipboard(target, false);
		__do_wipe_container(target, false);
		notify_user("info", "Eljárás áthelyezve a várólapra");
	}
	
	
	public void __do_server_execute(SaacApi RPC, HTMLElement target)
	{
		HTMLElement main = whereParentWithBoundary(target, ".saac_entry_point", ".saac_entry_point");
		if(null != main)
		{
			HTMLElement container = saac_find_container_dir_child(main);
			if(null != container)
			{
				RPC.execute
				(
					new SimplePublish2<DataObject, JvxClientException>()
					{
						@Override
						public void publish(DataObject ret, JvxClientException error)
						{
							if(null != error)
							{
								notify_user("error", "Szerver oldali hiba történt: "+error);
							}
							else if(null != ret)
							{
								notify_user("info", "Szerver oldali futtatás megkezdve.");
							}
							else
							{
								notify_user("error", "Belső hiba történt, részletekért lásd a javascript konzolt.");
							}
						}
					},
					saac_serialize_container
					(
						container,
						SaacSerializationMode.ALL,
						SaacSerializationMode.ALL
					)
				);
			}
		}
	}
	
	public void __do_varadic_add(HTMLElement target)
	{
		saac_add_varadic(whereParentWithBoundary(target, ".saac_argument", ".saac_entry_point"));
	}
	
	public void __do_varadic_remove(HTMLElement target)
	{
		remove(whereParentWithBoundary(target, ".saac_function_container", ".saac_entry_point"));
	}
	
	public void __do_arguments_viewmode_switch(HTMLElement target)
	{
		changeClass(target, "glyphicon-resize-vertical", "glyphicon-resize-horizontal");
		HTMLElement container = saac_find_container_dir_parent(target);
		ClassList cl = getClassList(target);
		if(cl.contains("glyphicon-resize-horizontal"))
		{
			cl.add("saac_viewmode_horizontal");
		}
		else
		{
			cl.remove("saac_viewmode_horizontal");
		}
	}
	
	protected HTMLElement consoleDom;
	
	public void __do_show_console(HTMLElement target)
	{
		//TODO
		/*HTMLElement top = saac_get_top_container(target);
		if(null != consoleDom)
		{
			return;
		}
		
		HTMLElement consoleContainer = top.querySelector(".saac_hidden_console_content");
		
		Window win = Window.current().open("", "B", "width=800,height=600");
		win.document.title = "Saac Console";
		win.document.body.appendChild(consoleContainer.children[0]);
		consoleContainer.innerHtml = "";
		
		win.onbeforeunload = function()
		{
			consoleContainer.appendChild(win.document.body.children[0]);
			
			top.console = undefined;
			top.consoleDom = undefined;
			target.classList.remove("saac_disabled");
		}
		
		top.console = win;
		top.consoleDom = win.document;
		target.classList.add("saac_disabled");*/
	}
	
	public void __saac_add_eventlistener_clicks()
	{
		target.addEventListener
		(
			"click",
			new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event event)
				{
					HTMLElement target = event.getTarget().cast();
					ClassList cl = getClassList(target);
					
					//prevent handling unwanted events
					if(!cl.contains("saac"))
					{
						return;
					}
					
					//dispatching menubar and events;
					if(cl.contains("saac_operation_all_clipboard"))
					{
						__do_copy_clipboard(target, true);
					}
					else if(cl.contains("saac_operation_remove"))
					{
						__do_wipe_container(target, true);
					}
					else if(cl.contains("saac_operation_clipboard_cut"))
					{
						__do_cut_clipboard(target);
					}
					else if(cl.contains("saac_operation_execute"))
					{
						__do_server_execute(rpc, target);
					}
					else if(cl.contains("saac_varadic_add"))
					{
						__do_varadic_add(target);
					}
					else if(cl.contains("saac_varadic_remove"))
					{
						__do_varadic_remove(target);
					}
					else if(cl.contains("saac_view_arguments_mode"))
					{
						__do_arguments_viewmode_switch(target);
					}
					else if(cl.contains("saac_operation_show_console"))
					{
						if(!cl.contains("saac_disabled"))
						{
							__do_show_console(target);
						}
					}
				}
			}
		);
	}
	
	public void __do_funtion_paste(String data)
	{
		try
		{
			HTMLElement container = null;
			if(getClassList(target).contains("saac_function_container"))
			{
				container = target;
			}
			else
			{
				container = saac_find_container_dir_parent(target);
			}
			
			DataObjectTeaVMImpl json = new DataObjectTeaVMImpl(JSON.parse(data));
			
			saac_assert_valid_restore_data(json);
			__do_wipe_container(container, false);
			saac_restore_function(container, json);
			notify_user("info", "Sikeres beillesztés");
		}
		catch(Exception err)
		{
			notify_user("error", err.getMessage());
			throw err;
		}
	}
	
	
	//TODO
	public void __saac_add_eventlistener_paste()
	{
		/*public static void pasteFocus(event)
		{
			var target = event.target;
			if(!target.classList.contains("saac_clipboard_insert_area"))
			{
				return;
			}
			
			var brd = target.whereParentWithBoundary(".saac_operation_insert_from_clipboard", ".saac_entry_point");//TODO saac_argument
			if(target === document.activeElement)
			{
				brd.classList.add("active");
				notify_user("info", "Beillesztés Ctrl+V megnyomására");
			}
			else
			{
				brd.classList.remove("active");
			}
		}
		
		document.body.addEventListener("focusin", pasteFocus);
		document.body.addEventListener("focusout", pasteFocus);
		
		document.body.addEventListener
		(
			"paste",
			function(event)
			{
				var target = event.target;
				//prevent handling unwanted events
				if(!target.classList.contains("saac_clipboard_insert_area"))
				{
					return;
				}
				event.preventDefault();
				
				//remove focus
				document.activeElement.blur();
				
				var data = event.clipboardData.getData('Text');
				
				var toCall = null;
				
				//the target is a textrate and the parent should be a "div"
				if(target.parentNode.classList.contains("saac_top_level_opearation"))
				{
					toCall = document.querySelector(".saac_function_container");
				}
				else
				{
					toCall = target
								.whereParentWithBoundary(".saac_argument", ".saac_entry_point")
								.querySelector(".saac_function_container");
				}
				
				__do_funtion_paste(toCall, data);
				
				return false;
			}
		);**/
	}
	
	protected static abstract class EventExtra implements JSObject
	{
		@JSProperty
		public abstract boolean isCtrlKey();
	}
	
	public void __saac_add_eventlistener_input_type()
	{
		target.addEventListener
		(
			"click",
			new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event event)
				{
					HTMLElement target = event.getTarget().cast();
					//prevent handling unwanted events
					ClassList cl = getClassList(target);
					
					if(!cl.contains("saac_function_autocomplete"))
					{
						return;
					}
					
					if(((EventExtra)(JSObject)event).isCtrlKey())
					{
						event.preventDefault();
						if(target.getNodeName().equals("INPUT"))
						{
							alterTag(target, "textarea");
						}
						else
						{
							cl.remove("saac_typeahead_wired");
							alterTag(target, "input");
						}
					}
				}
			}
		);
	}
	
	/************************** init helper functions *****************************/
	
	public static void __saac_init_show_menu(HTMLElement container, String cls_selector, boolean bool_show)
	{
		if
		(
			null != container
		&&
			null != cls_selector
		)
		{
			HTMLElement tar = container.querySelector(cls_selector);
			ClassList cl = getClassList(tar);
			if(null != tar)
			{
				if(bool_show)
				{
					cl.remove("saac_no_show");
				}
				else
				{
					cl.add("saac_no_show");
				}
			}
		}
	}
	
	protected static class SaacAcceptType
	{
		public final String func;
		public final DataObject type;
		
		public SaacAcceptType(DataObject func, int index)
		{
			this.func = func.getString("id")+":"+index;
			type = func.getArray("arguments").getObject(index).getObject("type");
			
/*				{
					func: func.id+":"+index,
					type: func.arguments[index].type 
				};
	*/		
		}
	}
	
	public static class SaacRootType
	{
		public final String func;
		public final int argIndex;
		
		public SaacRootType(String func, int index)
		{
			this.func = func;
			this.argIndex = index;
		}
	}
	
	public static class SaacMenuVisiblity
	{
		public boolean clear = true;
		public boolean cut = true;
		public boolean insert = true;
		public boolean copy = true;
		public boolean save = true;
		public boolean execute = true;
		public boolean console = true;
		public boolean browser = true;
		public boolean editor = true;
	}
	
	public static class SaacConfig
	{
		public SaacRootType rootType;
		public SaacMenuVisiblity show = new SaacMenuVisiblity();
		public boolean embedConsole;
		
		public SaacApi api;
		
		public SimplePublish2<String, String> customNotify;
	}
	
	public SaacAcceptType __saac_init_conf_get_root_type(SaacConfig cfg)
	{
		if(null != cfg)
		{
			if(null != cfg.rootType)
			{
				DataObject func = saac_get_function_by_id(cfg.rootType.func);
				
				if(null == func)
				{
					String errmsg = "function does'nt exists: "+cfg.rootType.func;
					System.err.println(errmsg);
					throw new RuntimeException(errmsg);
				}

				//checking argument boundary.
				int index = cfg.rootType.argIndex;
				
				if(index < 0 || index >= func.getArray("arguments").size())
				{
					String errmsg = "function ('"+func.getString("name")+"') accepts only "+func.getArray("arguments").size()+" arguments (length), "+index+" (index) given";
					System.err.println(errmsg);
					throw new RuntimeException(errmsg);
				}
				return new SaacAcceptType(func, index);
			}
		}
		
		return null;
	}
	
	public DataObject serialize()
	{
		return saac_serialize_container
		(
			saac_find_container_dir_child(target),
			SaacSerializationMode.ALL,
			SaacSerializationMode.ALL
		);
	}
	
	public void __saac_init_options_visibility(HTMLElement root, SaacConfig cfg)
	{
		if(null != cfg && null != cfg.show)
		{
			__saac_init_show_menu(root, ".saac_operation_remove", cfg.show.clear);
			__saac_init_show_menu(root, ".saac_operation_clipboard_cut", cfg.show.cut);
			__saac_init_show_menu(root, ".saac_operation_insert_from_clipboard", cfg.show.insert);
			__saac_init_show_menu(root, ".saac_operation_all_clipboard", cfg.show.copy);
			__saac_init_show_menu(root, ".saac_operation_save", cfg.show.save);
			__saac_init_show_menu(root, ".saac_operation_execute", cfg.show.execute);
			__saac_init_show_menu(root, ".saac_operation_show_console", cfg.show.console);
			__saac_init_show_menu(root, ".saac_project_file_container", cfg.show.browser);
			__saac_init_show_menu(root, ".saac_primary_container", cfg.show.editor);
		}	
		
		if(null != cfg && cfg.embedConsole)
		{
			__saac_init_show_menu(root, ".saac_hidden_console_content", cfg.embedConsole);
		}
		
		__saac_add_eventlistener_autocomplete();
		__saac_add_eventlistener_argument_sortable();
		__saac_add_eventlistener_clicks();
		__saac_add_eventlistener_paste();
		__saac_add_eventlistener_input_type();
		__saac_add_eventlistener_textarea();
	}
	
	/************************* Remote filesystem support **************************/
	
	/*
	
	FileEntry:
	{
		type: ( regular, directory, special)
		size: (in bytes)
		name: (last part of file path)
		path: (full path including filename)
	}
	
	RemoteFileSystem:
	{
		stat = function(path): FileEntry
		listFiles = function(path): FileEntry[]
		getFileContent = function(path): Blob
		setFileContent = function(path, Blob): boolean
	}
	
	*/
	
	
	/******************** module/main contain initialization  **********************/
	
	public HTMLElement saac_get_console_root(HTMLElement container)
	{
		HTMLElement tar = container.querySelector(".saac_console_root");
		
		//still embedded
		if(null != tar)
		{
			return tar;
		}
		else
		{
			return consoleDom;
		}
	}
	
	public HTMLElement saac_get_log_container(HTMLElement container)
	{
		return saac_get_console_root(container).querySelector(".saac_log_container");
	}
	
	public static void saac_dispatch_server_event(HTMLElement container, DataObject packet)
	{
		/*TODO if(!container.classList.contains("saac_entry_point"))
		{
			container = saac_get_top_container(container);
		}
		
		var evt = JSON.parse(packet[0]);
		
		var _ = evt._;
		var func = evt.f;
		var args = evt.p;
		
		var listener = container.serverEvents;
		
		if(is_function(listener))
		{
			listener(func, args);
		}
		
		if("newLogLine" == func)
		{
			console.log("LOGGING LINE");
			var div = parseHtml("<div></div>");
			div.innerText = args[0];
			var cli = saac_get_log_container(container).appendChild(div);
		}
		//else
		{
			console.log(["server event:", container, _, func, args]);
		}*/
	}
	
	/**
	 * 
	 * config:
	 * 	- show: [boolean]
	 * 	{
	 * 		clear, cut, insert, copy, execute, console, browser 
	 * 	}
	 * 	- embedConsole: if true, hidden console shown
	 * 	- rootType: {func: "id_or_function_name", argIndex: "nth argument of function"}
	 * 	- projectFileSupport: TODO
	 * 	- loadFile: (path, used only if projectFileSupport provided)
	 * */
	public static SaacEditor saac_initEntryPoint
	(
		HTMLElement root,
		SaacConfig cfg
	)
	{
		SaacEditor ret = new SaacEditor(root, cfg);
		ret.synch_refresh_function_list();
		/*ret.__saac_refresh_function_list(new SimplePublish1<List<DataObject>>()
		{
			@Override
			public void publish(List<DataObject> a)
			{*/
				SaacAcceptType root_type = ret.__saac_init_conf_get_root_type(cfg);
				
				//setting the root type.
				if(null != root_type)
				{
					setInnerText(root.querySelector(".saac_root_accept_type"), root_type.func);
					setInnerText(root.querySelector(".saac_root_type_name"), saac_type_render(root_type.type));
				}
			/*}
		});*/
		ret.__saac_init_options_visibility(root, cfg);
		
		///////////// Creating the main container ///////////// 
		root.querySelector(".saac_primary_container").appendChild(parseHtml(ret.renderTemplate("saac/function_container")));
		
		return ret;
	}
	
	
	public static SaacEditor saac_createEntryPoint(HTMLElement ROOT, SaacConfig cfg)
	{
		HTMLElement tar = parseHtml(renderTemplate("saac/main"));
		ROOT.appendChild(tar);
		return saac_initEntryPoint(tar, cfg);
	}

	public void ensureInitalized(SimplePublish1<SaacEditor> onReady)
	{
		synch_refresh_function_list();
		
		/*__saac_refresh_function_list(new SimplePublish1<List<DataObject>>()
		{
			@Override
			public void publish(List<DataObject> a)
			{*/
				if(null != onReady)
				{
					onReady.publish(SaacEditor.this);
				}
			/*}
		});*/
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return "SaacEditor: "+name;
	}
}
