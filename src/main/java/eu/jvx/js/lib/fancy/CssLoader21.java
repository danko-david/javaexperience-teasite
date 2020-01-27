package eu.jvx.js.lib.fancy;

import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.S;
import eu.jvx.js.lib.style.StyleCollection;

/**
 * Source: http://cssloaders.com/21
 * Sadly the site is not hosted anymore :(
 * */
public class CssLoader21
{
	protected static final StyleCollection STYLE = StyleCollection.createStyleDomCollection();
	
	static
	{
		STYLE.addStyle(".cssLoader21-main-wrap{background-color: rgba(128, 128, 128, 0.5);display: block;position: absolute;top: 0;width: 100%;height: 100%;color: #fff;justify-content:center;align-items:center;}");
		STYLE.addStyle(".cssLoader21-loader{border-radius: 50%;position: absolute;height: 200px;left: 0;margin-left: 0;position: relative;right: 0;top: 5%;width: 200px;}");
		STYLE.addStyle(".cssLoader21-showbox{top: 0;bottom: 0;left: 0;right: 0;padding: 5%;}");
		STYLE.addStyle(".cssLoader21-loader21{position: relative;margin: 0 auto;width: 100px;}");
		STYLE.addStyle(".cssLoader21-loader21:before{content: '';display: block;padding-top: 100%;}");
		STYLE.addStyle(".cssLoader21-circular{animation: cssLoader21-rotate 2s linear infinite;height: 100%;transform-origin: center center;width: 100%;position: absolute;top: 0;bottom: 0;left: 0;right: 0;margin: auto;}");
		STYLE.addStyle(".cssLoader21-path{stroke-dasharray: 1, 200;stroke-dashoffset: 0;animation: cssLoader21-dash 1.5s ease-in-out infinite, cssLoader21-color 6s ease-in-out infinite;stroke-linecap: round;}");
		STYLE.addStyle("@keyframes cssLoader21-rotate{100%{transform: rotate(360deg);}}");
		STYLE.addStyle("@keyframes cssLoader21-dash{0%{stroke-dasharray: 1, 200;stroke-dashoffset: 0;}50%{stroke-dasharray: 89, 200;stroke-dashoffset: -35px;}100%{stroke-dasharray: 89, 200;stroke-dashoffset: -124px;}}");
		STYLE.addStyle("@keyframes cssLoader21-color {100%,0%{stroke: #d62d20;}40%{stroke: #0057e7;}66%{stroke: #008744;}80%,90%{stroke: #ffa700;}}");
	}
	
	public static H createLoader()
	{
		return new H("div").attrs("class", "cssLoader21-main-wrap").addChilds
		(
			new H("div").attrs("class", "cssLoader21-showbox").addChilds
			(
				new H("div").attrs("class", "cssLoader21-loader21").addChilds
				(
					new S("svg").attrs("class", "cssLoader21-circular", "viewBox", "25 25 50 50").addChilds
					(
						new S("circle").attrs
						(
							"class", "cssLoader21-path",
							"cx", "50",
							"cy", "50",
							"r", "20",
							"fill", "none",
							"stroke-width", "2",
							"stroke-miterlimit", "10"
						)
					)
				)
			)
		);
	}
}
