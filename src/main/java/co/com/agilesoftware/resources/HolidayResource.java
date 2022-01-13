package co.com.agilesoftware.resources;

import co.com.agilesoftware.service.HolidayValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Path("holiday")
public class HolidayResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean isHoliday(@QueryParam("date") Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return HolidayValidator.isHoliday(localDate);
    }

    @GET
    @Path("/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LocalDate> getHolidays(@PathParam("year") Integer year) {
        return HolidayValidator.getHoliDays(year);
    }
}
