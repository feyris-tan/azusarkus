package moe.yo3explorer.azusa.skyscraper.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.qute.TemplateExtension;
import moe.yo3explorer.azusa.skyscraper.entity.NetworkId;
import moe.yo3explorer.azusa.skyscraper.entity.Service;
import moe.yo3explorer.azusa.skyscraper.entity.Transponder;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.List;

@TemplateExtension
public class SkyscraperTemplateExtension {
    static String resolveSatNetwork(Transponder transponder)
    {
        if (transponder.network == null)
            return "";

        NetworkId byId = NetworkId.findById(transponder.network);
        if (byId != null)
            return byId.name;

        return String.format("Unknown network 0x%04X (%d)",transponder.network,transponder.network);
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

    static List<Service> getServices(@NotNull Transponder transponder)
    {
        if (transponder.serviceList == null)
        {
            transponder.serviceList = Service.find("transponder = ?1", transponder.id).list();
        }
        return transponder.serviceList;
    }

    static String renderLastSeen(Transponder transponder)
    {
        if (transponder.lastvalid == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(transponder.lastvalid);
    }

    static String renderLastSeen(Service service)
    {
        if (service.lastseen == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(service.lastseen);
    }
}
