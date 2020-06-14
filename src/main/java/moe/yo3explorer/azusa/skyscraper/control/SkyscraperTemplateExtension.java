package moe.yo3explorer.azusa.skyscraper.control;

import io.quarkus.qute.TemplateExtension;
import moe.yo3explorer.azusa.skyscraper.entity.Transponder;

@TemplateExtension
public class SkyscraperTemplateExtension {
    static String resolveSatNetwork(Transponder transponder)
    {
        if (transponder.network == null)
            return "";

        return String.format("Unknown network %d",transponder.network);
    }


    static int renderFrequency(Transponder transponder)
    {
        return (int)(transponder.frequency);
    }

    static String renderPolarization(Transponder transponder)
    {
        if (transponder.polarization.equals("HORIZONTAL"))
            return "H";
        else if (transponder.polarization.equals("VERTICAL"))
            return "V";
        else
            return transponder.polarization;
    }

    static String renderSystem(Transponder transponder)
    {
        return transponder.s2 ? "DVB-S2" : "DVB-S";
    }

    static String renderFec(Transponder transponder)
    {
        if (transponder.fec.equals("_2_3"))
            return "\u2154";
        if (transponder.fec.equals("_5_6"))
            return "\u215A";
        if (transponder.fec.equals("_3_4"))
            return "\u00BE";
        if (transponder.fec.equals("_7_8"))
            return "\u215E";
        if (transponder.fec.equals("_9_10"))
            return "\u2079\u2044\u2081\u2080";
        return transponder.fec;
    }

    static String renderModulation(Transponder transponder)
    {
        String result = transponder.modulation;
        if (result.startsWith("_"))
            result = result.substring(1);
        return result;
    }
}
