package eu.jvx.js.tbs;

import eu.jvx.js.lib.style.StyleAlaCarteMenu;
import eu.jvx.js.lib.style.StyleDecorator;
import eu.jvx.js.lib.style.StyleDecoratorSource;

/**
 * https://getbootstrap.com/docs/3.3/components/
 * http://glyphicons.com/
 * */
public enum TbsGlyph implements StyleDecoratorSource
{

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-asterisk"></span>
	 **/
	ASTERISK("asterisk"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-plus"></span>
	 **/
	PLUS("plus"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-euro"></span>
	 **/
	EURO("euro"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-eur"></span>
	 **/
	EUR("eur"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-minus"></span>
	 **/
	MINUS("minus"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-cloud"></span>
	 **/
	CLOUD("cloud"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-envelope"></span>
	 **/
	ENVELOPE("envelope"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-pencil"></span>
	 **/
	PENCIL("pencil"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-glass"></span>
	 **/
	GLASS("glass"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-music"></span>
	 **/
	MUSIC("music"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-search"></span>
	 **/
	SEARCH("search"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-heart"></span>
	 **/
	HEART("heart"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-star"></span>
	 **/
	STAR("star"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-star-empty"></span>
	 **/
	STAR_EMPTY("star-empty"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-user"></span>
	 **/
	USER("user"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-film"></span>
	 **/
	FILM("film"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-th-large"></span>
	 **/
	TH_LARGE("th-large"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-th"></span>
	 **/
	TH("th"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-th-list"></span>
	 **/
	TH_LIST("th-list"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ok"></span>
	 **/
	OK("ok"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-remove"></span>
	 **/
	REMOVE("remove"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-zoom-in"></span>
	 **/
	ZOOM_IN("zoom-in"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-zoom-out"></span>
	 **/
	ZOOM_OUT("zoom-out"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-off"></span>
	 **/
	OFF("off"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-signal"></span>
	 **/
	SIGNAL("signal"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-cog"></span>
	 **/
	COG("cog"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-trash"></span>
	 **/
	TRASH("trash"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-home"></span>
	 **/
	HOME("home"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-file"></span>
	 **/
	FILE("file"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-time"></span>
	 **/
	TIME("time"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-road"></span>
	 **/
	ROAD("road"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-download-alt"></span>
	 **/
	DOWNLOAD_ALT("download-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-download"></span>
	 **/
	DOWNLOAD("download"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-upload"></span>
	 **/
	UPLOAD("upload"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-inbox"></span>
	 **/
	INBOX("inbox"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-play-circle"></span>
	 **/
	PLAY_CIRCLE("play-circle"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-repeat"></span>
	 **/
	REPEAT("repeat"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-refresh"></span>
	 **/
	REFRESH("refresh"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-list-alt"></span>
	 **/
	LIST_ALT("list-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-lock"></span>
	 **/
	LOCK("lock"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-flag"></span>
	 **/
	FLAG("flag"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-headphones"></span>
	 **/
	HEADPHONES("headphones"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-volume-off"></span>
	 **/
	VOLUME_OFF("volume-off"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-volume-down"></span>
	 **/
	VOLUME_DOWN("volume-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-volume-up"></span>
	 **/
	VOLUME_UP("volume-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-qrcode"></span>
	 **/
	QRCODE("qrcode"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-barcode"></span>
	 **/
	BARCODE("barcode"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tag"></span>
	 **/
	TAG("tag"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tags"></span>
	 **/
	TAGS("tags"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-book"></span>
	 **/
	BOOK("book"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bookmark"></span>
	 **/
	BOOKMARK("bookmark"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-print"></span>
	 **/
	PRINT("print"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-camera"></span>
	 **/
	CAMERA("camera"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-font"></span>
	 **/
	FONT("font"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bold"></span>
	 **/
	BOLD("bold"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-italic"></span>
	 **/
	ITALIC("italic"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-text-height"></span>
	 **/
	TEXT_HEIGHT("text-height"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-text-width"></span>
	 **/
	TEXT_WIDTH("text-width"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-align-left"></span>
	 **/
	ALIGN_LEFT("align-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-align-center"></span>
	 **/
	ALIGN_CENTER("align-center"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-align-right"></span>
	 **/
	ALIGN_RIGHT("align-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-align-justify"></span>
	 **/
	ALIGN_JUSTIFY("align-justify"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-list"></span>
	 **/
	LIST("list"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-indent-left"></span>
	 **/
	INDENT_LEFT("indent-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-indent-right"></span>
	 **/
	INDENT_RIGHT("indent-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-facetime-video"></span>
	 **/
	FACETIME_VIDEO("facetime-video"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-picture"></span>
	 **/
	PICTURE("picture"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-map-marker"></span>
	 **/
	MAP_MARKER("map-marker"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-adjust"></span>
	 **/
	ADJUST("adjust"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tint"></span>
	 **/
	TINT("tint"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-edit"></span>
	 **/
	EDIT("edit"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-share"></span>
	 **/
	SHARE("share"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-check"></span>
	 **/
	CHECK("check"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-move"></span>
	 **/
	MOVE("move"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-step-backward"></span>
	 **/
	STEP_BACKWARD("step-backward"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-fast-backward"></span>
	 **/
	FAST_BACKWARD("fast-backward"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-backward"></span>
	 **/
	BACKWARD("backward"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-play"></span>
	 **/
	PLAY("play"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-pause"></span>
	 **/
	PAUSE("pause"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-stop"></span>
	 **/
	STOP("stop"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-forward"></span>
	 **/
	FORWARD("forward"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-fast-forward"></span>
	 **/
	FAST_FORWARD("fast-forward"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-step-forward"></span>
	 **/
	STEP_FORWARD("step-forward"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-eject"></span>
	 **/
	EJECT("eject"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-chevron-left"></span>
	 **/
	CHEVRON_LEFT("chevron-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-chevron-right"></span>
	 **/
	CHEVRON_RIGHT("chevron-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-plus-sign"></span>
	 **/
	PLUS_SIGN("plus-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-minus-sign"></span>
	 **/
	MINUS_SIGN("minus-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-remove-sign"></span>
	 **/
	REMOVE_SIGN("remove-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ok-sign"></span>
	 **/
	OK_SIGN("ok-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-question-sign"></span>
	 **/
	QUESTION_SIGN("question-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-info-sign"></span>
	 **/
	INFO_SIGN("info-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-screenshot"></span>
	 **/
	SCREENSHOT("screenshot"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-remove-circle"></span>
	 **/
	REMOVE_CIRCLE("remove-circle"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ok-circle"></span>
	 **/
	OK_CIRCLE("ok-circle"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ban-circle"></span>
	 **/
	BAN_CIRCLE("ban-circle"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-arrow-left"></span>
	 **/
	ARROW_LEFT("arrow-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-arrow-right"></span>
	 **/
	ARROW_RIGHT("arrow-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-arrow-up"></span>
	 **/
	ARROW_UP("arrow-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-arrow-down"></span>
	 **/
	ARROW_DOWN("arrow-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-share-alt"></span>
	 **/
	SHARE_ALT("share-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-resize-full"></span>
	 **/
	RESIZE_FULL("resize-full"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-resize-small"></span>
	 **/
	RESIZE_SMALL("resize-small"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-exclamation-sign"></span>
	 **/
	EXCLAMATION_SIGN("exclamation-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-gift"></span>
	 **/
	GIFT("gift"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-leaf"></span>
	 **/
	LEAF("leaf"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-fire"></span>
	 **/
	FIRE("fire"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-eye-open"></span>
	 **/
	EYE_OPEN("eye-open"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-eye-close"></span>
	 **/
	EYE_CLOSE("eye-close"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-warning-sign"></span>
	 **/
	WARNING_SIGN("warning-sign"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-plane"></span>
	 **/
	PLANE("plane"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-calendar"></span>
	 **/
	CALENDAR("calendar"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-random"></span>
	 **/
	RANDOM("random"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-comment"></span>
	 **/
	COMMENT("comment"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-magnet"></span>
	 **/
	MAGNET("magnet"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-chevron-up"></span>
	 **/
	CHEVRON_UP("chevron-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-chevron-down"></span>
	 **/
	CHEVRON_DOWN("chevron-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-retweet"></span>
	 **/
	RETWEET("retweet"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-shopping-cart"></span>
	 **/
	SHOPPING_CART("shopping-cart"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-folder-close"></span>
	 **/
	FOLDER_CLOSE("folder-close"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-folder-open"></span>
	 **/
	FOLDER_OPEN("folder-open"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-resize-vertical"></span>
	 **/
	RESIZE_VERTICAL("resize-vertical"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-resize-horizontal"></span>
	 **/
	RESIZE_HORIZONTAL("resize-horizontal"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hdd"></span>
	 **/
	HDD("hdd"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bullhorn"></span>
	 **/
	BULLHORN("bullhorn"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bell"></span>
	 **/
	BELL("bell"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-certificate"></span>
	 **/
	CERTIFICATE("certificate"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-thumbs-up"></span>
	 **/
	THUMBS_UP("thumbs-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-thumbs-down"></span>
	 **/
	THUMBS_DOWN("thumbs-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hand-right"></span>
	 **/
	HAND_RIGHT("hand-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hand-left"></span>
	 **/
	HAND_LEFT("hand-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hand-up"></span>
	 **/
	HAND_UP("hand-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hand-down"></span>
	 **/
	HAND_DOWN("hand-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-circle-arrow-right"></span>
	 **/
	CIRCLE_ARROW_RIGHT("circle-arrow-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-circle-arrow-left"></span>
	 **/
	CIRCLE_ARROW_LEFT("circle-arrow-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-circle-arrow-up"></span>
	 **/
	CIRCLE_ARROW_UP("circle-arrow-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-circle-arrow-down"></span>
	 **/
	CIRCLE_ARROW_DOWN("circle-arrow-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-globe"></span>
	 **/
	GLOBE("globe"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-wrench"></span>
	 **/
	WRENCH("wrench"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tasks"></span>
	 **/
	TASKS("tasks"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-filter"></span>
	 **/
	FILTER("filter"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-briefcase"></span>
	 **/
	BRIEFCASE("briefcase"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-fullscreen"></span>
	 **/
	FULLSCREEN("fullscreen"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-dashboard"></span>
	 **/
	DASHBOARD("dashboard"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-paperclip"></span>
	 **/
	PAPERCLIP("paperclip"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-heart-empty"></span>
	 **/
	HEART_EMPTY("heart-empty"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-link"></span>
	 **/
	LINK("link"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-phone"></span>
	 **/
	PHONE("phone"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-pushpin"></span>
	 **/
	PUSHPIN("pushpin"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-usd"></span>
	 **/
	USD("usd"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-gbp"></span>
	 **/
	GBP("gbp"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort"></span>
	 **/
	SORT("sort"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort-by-alphabet"></span>
	 **/
	SORT_BY_ALPHABET("sort-by-alphabet"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort-by-alphabet-alt"></span>
	 **/
	SORT_BY_ALPHABET_ALT("sort-by-alphabet-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort-by-order"></span>
	 **/
	SORT_BY_ORDER("sort-by-order"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort-by-order-alt"></span>
	 **/
	SORT_BY_ORDER_ALT("sort-by-order-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort-by-attributes"></span>
	 **/
	SORT_BY_ATTRIBUTES("sort-by-attributes"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
	 **/
	SORT_BY_ATTRIBUTES_ALT("sort-by-attributes-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-unchecked"></span>
	 **/
	UNCHECKED("unchecked"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-expand"></span>
	 **/
	EXPAND("expand"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-collapse-down"></span>
	 **/
	COLLAPSE_DOWN("collapse-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-collapse-up"></span>
	 **/
	COLLAPSE_UP("collapse-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-log-in"></span>
	 **/
	LOG_IN("log-in"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-flash"></span>
	 **/
	FLASH("flash"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-log-out"></span>
	 **/
	LOG_OUT("log-out"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-new-window"></span>
	 **/
	NEW_WINDOW("new-window"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-record"></span>
	 **/
	RECORD("record"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-save"></span>
	 **/
	SAVE("save"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-open"></span>
	 **/
	OPEN("open"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-saved"></span>
	 **/
	SAVED("saved"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-import"></span>
	 **/
	IMPORT("import"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-export"></span>
	 **/
	EXPORT("export"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-send"></span>
	 **/
	SEND("send"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-floppy-disk"></span>
	 **/
	FLOPPY_DISK("floppy-disk"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-floppy-saved"></span>
	 **/
	FLOPPY_SAVED("floppy-saved"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-floppy-remove"></span>
	 **/
	FLOPPY_REMOVE("floppy-remove"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-floppy-save"></span>
	 **/
	FLOPPY_SAVE("floppy-save"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-floppy-open"></span>
	 **/
	FLOPPY_OPEN("floppy-open"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-credit-card"></span>
	 **/
	CREDIT_CARD("credit-card"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-transfer"></span>
	 **/
	TRANSFER("transfer"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-cutlery"></span>
	 **/
	CUTLERY("cutlery"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-header"></span>
	 **/
	HEADER("header"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-compressed"></span>
	 **/
	COMPRESSED("compressed"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-earphone"></span>
	 **/
	EARPHONE("earphone"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-phone-alt"></span>
	 **/
	PHONE_ALT("phone-alt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tower"></span>
	 **/
	TOWER("tower"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-stats"></span>
	 **/
	STATS("stats"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sd-video"></span>
	 **/
	SD_VIDEO("sd-video"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hd-video"></span>
	 **/
	HD_VIDEO("hd-video"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-subtitles"></span>
	 **/
	SUBTITLES("subtitles"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sound-stereo"></span>
	 **/
	SOUND_STEREO("sound-stereo"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sound-dolby"></span>
	 **/
	SOUND_DOLBY("sound-dolby"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sound-5-1"></span>
	 **/
	SOUND_5_1("sound-5-1"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sound-6-1"></span>
	 **/
	SOUND_6_1("sound-6-1"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sound-7-1"></span>
	 **/
	SOUND_7_1("sound-7-1"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-copyright-mark"></span>
	 **/
	COPYRIGHT_MARK("copyright-mark"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-registration-mark"></span>
	 **/
	REGISTRATION_MARK("registration-mark"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-cloud-download"></span>
	 **/
	CLOUD_DOWNLOAD("cloud-download"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-cloud-upload"></span>
	 **/
	CLOUD_UPLOAD("cloud-upload"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tree-conifer"></span>
	 **/
	TREE_CONIFER("tree-conifer"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tree-deciduous"></span>
	 **/
	TREE_DECIDUOUS("tree-deciduous"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-cd"></span>
	 **/
	CD("cd"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-save-file"></span>
	 **/
	SAVE_FILE("save-file"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-open-file"></span>
	 **/
	OPEN_FILE("open-file"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-level-up"></span>
	 **/
	LEVEL_UP("level-up"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-copy"></span>
	 **/
	COPY("copy"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-paste"></span>
	 **/
	PASTE("paste"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-alert"></span>
	 **/
	ALERT("alert"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-equalizer"></span>
	 **/
	EQUALIZER("equalizer"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-king"></span>
	 **/
	KING("king"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-queen"></span>
	 **/
	QUEEN("queen"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-pawn"></span>
	 **/
	PAWN("pawn"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bishop"></span>
	 **/
	BISHOP("bishop"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-knight"></span>
	 **/
	KNIGHT("knight"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-baby-formula"></span>
	 **/
	BABY_FORMULA("baby-formula"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-tent"></span>
	 **/
	TENT("tent"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-blackboard"></span>
	 **/
	BLACKBOARD("blackboard"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bed"></span>
	 **/
	BED("bed"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-apple"></span>
	 **/
	APPLE("apple"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-erase"></span>
	 **/
	ERASE("erase"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-hourglass"></span>
	 **/
	HOURGLASS("hourglass"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-lamp"></span>
	 **/
	LAMP("lamp"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-duplicate"></span>
	 **/
	DUPLICATE("duplicate"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-piggy-bank"></span>
	 **/
	PIGGY_BANK("piggy-bank"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-scissors"></span>
	 **/
	SCISSORS("scissors"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-bitcoin"></span>
	 **/
	BITCOIN("bitcoin"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-btc"></span>
	 **/
	BTC("btc"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-xbt"></span>
	 **/
	XBT("xbt"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-yen"></span>
	 **/
	YEN("yen"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-jpy"></span>
	 **/
	JPY("jpy"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ruble"></span>
	 **/
	RUBLE("ruble"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-rub"></span>
	 **/
	RUB("rub"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-scale"></span>
	 **/
	SCALE("scale"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ice-lolly"></span>
	 **/
	ICE_LOLLY("ice-lolly"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-ice-lolly-tasted"></span>
	 **/
	ICE_LOLLY_TASTED("ice-lolly-tasted"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-education"></span>
	 **/
	EDUCATION("education"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-option-horizontal"></span>
	 **/
	OPTION_HORIZONTAL("option-horizontal"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-option-vertical"></span>
	 **/
	OPTION_VERTICAL("option-vertical"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-menu-hamburger"></span>
	 **/
	MENU_HAMBURGER("menu-hamburger"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-modal-window"></span>
	 **/
	MODAL_WINDOW("modal-window"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-oil"></span>
	 **/
	OIL("oil"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-grain"></span>
	 **/
	GRAIN("grain"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-sunglasses"></span>
	 **/
	SUNGLASSES("sunglasses"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-text-size"></span>
	 **/
	TEXT_SIZE("text-size"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-text-color"></span>
	 **/
	TEXT_COLOR("text-color"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-text-background"></span>
	 **/
	TEXT_BACKGROUND("text-background"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-object-align-top"></span>
	 **/
	OBJECT_ALIGN_TOP("object-align-top"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-object-align-bottom"></span>
	 **/
	OBJECT_ALIGN_BOTTOM("object-align-bottom"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-object-align-horizontal"></span>
	 **/
	OBJECT_ALIGN_HORIZONTAL("object-align-horizontal"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-object-align-left"></span>
	 **/
	OBJECT_ALIGN_LEFT("object-align-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-object-align-vertical"></span>
	 **/
	OBJECT_ALIGN_VERTICAL("object-align-vertical"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-object-align-right"></span>
	 **/
	OBJECT_ALIGN_RIGHT("object-align-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-triangle-right"></span>
	 **/
	TRIANGLE_RIGHT("triangle-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-triangle-left"></span>
	 **/
	TRIANGLE_LEFT("triangle-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-triangle-bottom"></span>
	 **/
	TRIANGLE_BOTTOM("triangle-bottom"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-triangle-top"></span>
	 **/
	TRIANGLE_TOP("triangle-top"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-console"></span>
	 **/
	CONSOLE("console"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-superscript"></span>
	 **/
	SUPERSCRIPT("superscript"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-subscript"></span>
	 **/
	SUBSCRIPT("subscript"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-menu-left"></span>
	 **/
	MENU_LEFT("menu-left"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-menu-right"></span>
	 **/
	MENU_RIGHT("menu-right"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-menu-down"></span>
	 **/
	MENU_DOWN("menu-down"),

	/***
	 * <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /><span class="glyphicon glyphicon-menu-up"></span>
	 **/
	MENU_UP("menu-up"),
	;
	
	protected String shortClassName;
	
	protected StyleAlaCarteMenu asMenu;
	
	private TbsGlyph(String a)
	{
		shortClassName = a;
		asMenu = new StyleAlaCarteMenu("glyphicon", getCssClassName());
	}
	
	@Override
	public StyleDecorator getDecorator()
	{
		return asMenu;
	}

	public String getCssClassName()
	{
		return "glyphicon-"+shortClassName;
	}
}
