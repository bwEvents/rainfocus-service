package com.servicenow.servicenowrf.rainfocus.helpers;

import com.servicenow.servicenowrf.rainfocus.dto.Event;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@UtilityClass
public class EventHelper {

    private static final List<Event> events = Arrays.asList(
            new Event("newyork",
                    "k22newyork",
                    "New York",
                    "New York",
                    "Come see why the world works with ServiceNow. Join us from May 11 – 12 for two full days of networking, roundtable discussions, demos, breakouts, and more.",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/New_York_agenda%202_1646001533606001GxoI.png",
                    "Experience the energy and camaraderie of our community of experts. Gain practical guidance. Get hands-on training. Network with the top visionaries in your region. Explore our in-person event agenda and plan your perfect experience.",
                    "$1,195 USD",
                    "$795 USD",
                    "$995 USD (add-on)",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/Newyork_Teaser_16465056379120012QRI.mp4",
                    "https://reg.servicenow.com/flow/servicenow/k22newyork/myinfo",
                    "KnowledgeNewYork@servicenow.com",
                    "Knowledge will take place at four locations across the globe during the first half of 2022. Our New York City event will be held at the Jacob Javits Center on May 11-12, 2022."
            ),
            new Event("netherlands",
                    "k22netherlands",
                    "The Hague",
                    "The Hague",
                    "Come see why the world works with ServiceNow. Join us from 11 – 12 May for two full days of networking, roundtable discussions, demos, breakouts, and more.",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/Hague_agenda%202_1646088350581001GKga.png",
                    "Experience the energy and camaraderie of our community of experts. Gain practical guidance. Get hands-on training. Network with the top visionaries in your region. Explore our in-person event agenda and plan your perfect experience.",
                    "€1095 EUR",
                    "€695 EUR",
                    "€895 EUR (add-on)",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/Netherlands_Teaser_16465052248360018WdJ.mp4",
                    "https://reg.servicenow.com/flow/servicenow/k22netherlands/myinfo",
                    "KnowledgeHague@servicenow.com",
                    "Knowledge will take place at four locations across the globe during the first half of 2022. Our event being held at The Hague, Netherlands will take place at the World Forum on May 11-12, 2022."
            ),
            new Event("lasvegas",
                    "k22lasvegas",
                    "Las Vegas",
                    "Las Vegas",
                    "Come see why the world works with ServiceNow. Join us from May 25 – 26 for two full days of networking, roundtable discussions, demos, breakouts, and more.",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/las_vegas_agenda%202_16460884932690019vPJ.png",
                    "Experience the energy and camaraderie of our community of experts. Gain practical guidance. Get hands-on training. Network with the top visionaries in your region. Explore our in-person event agenda and plan your perfect experience.",
                    "$1,195 USD",
                    "$795 USD",
                    "$995 USD (add-on)",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/Lasvegas_Teaser_164650563564200120lM.mp4",
                    "https://reg.servicenow.com/flow/servicenow/k22lasvegas/myinfo",
                    "KnowledgeVegas@servicenow.com",
                    "Knowledge will take place at four locations across the globe during the first half of 2022. Our Las Vegas, Nevada event will be held at the Venetian Expo and Convention Center on May 25-26, 2022."
            ),
            new Event("australia",
                    "k22sydney",
                    "Sydney",
                    "Sydney",
                    "Come see why the world works with ServiceNow. Join us from 25 – 26 May for two full days of networking, roundtable discussions, demos, breakouts, and more.",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/Sydney_agenda%201_1646088552994001KAdl.png",
                    "Experience the energy and camaraderie of our community of experts. Gain practical guidance. Get hands-on training. Network with the top visionaries in your region. Explore our in-person event agenda and plan your perfect experience.",
                    "$1,595 AUD",
                    "$425 AUD",
                    "$1,295 AUD (add-on)",
                    "https://static.rainfocus.com/servicenow/knowledge2022/static/staticfile/staticfile/Australia_Teaser_1646505298697001qDQx.mp4",
                    "https://reg.servicenow.com/flow/servicenow/k22sydney/myinfo",
                    "KnowledgeSydney@servicenow.com",
                    "Knowledge will take place at four locations across the globe during the first half of 2022. Our Sydney, Australia event will be held at the International Convention Center on May 25-26, 2022."

            )

    );

    public static Event getEventBasedOnEventName(String eventName) {
        Optional<Event> matchingEvent = events.stream().filter(event -> event.getEventName()
                .equalsIgnoreCase(eventName)).findFirst();
        if (matchingEvent.isPresent()) {
            return matchingEvent.get();
        }
        log.warn("Event Not Found");
        return null;
    }

    public static String getStateAbbreviation(String state) {
        Map<String, String> statesMap = new HashMap<>();
        statesMap.put("Alaska", "AK");
        statesMap.put("Alabama", "AL");
        statesMap.put("Arkansas", "AR");
        statesMap.put("Arizona", "AZ");
        statesMap.put("California", "CA");
        statesMap.put("Colorado", "CO");
        statesMap.put("Connecticut", "CT");
        statesMap.put("District Of Columbia", "DC");
        statesMap.put("Delaware", "DE");
        statesMap.put("Florida", "FL");
        statesMap.put("Georgia", "GA");
        statesMap.put("Hawaii", "HI");
        statesMap.put("Iowa", "IA");
        statesMap.put("Idaho", "ID");
        statesMap.put("Illinois", "IL");
        statesMap.put("Indiana", "IN");
        statesMap.put("Kansas", "KS");
        statesMap.put("Kentucky", "KY");
        statesMap.put("Louisiana", "LA");
        statesMap.put("Massachusetts", "MA");
        statesMap.put("Maryland", "MD");
        statesMap.put("Maine", "ME");
        statesMap.put("Michigan", "MI");
        statesMap.put("Minnesota", "MN");
        statesMap.put("Missouri", "MO");
        statesMap.put("North Carolina", "NC");
        statesMap.put("North Dakota", "ND");
        statesMap.put("Nebraska", "NE");
        statesMap.put("New Hampshire", "NH");
        statesMap.put("New Jersey", "NJ");
        statesMap.put("New Mexico", "NM");
        statesMap.put("Nevada", "NV");
        statesMap.put("New York", "NY");
        statesMap.put("Oregon", "OR");
        statesMap.put("Pennsylvania", "PA");
        statesMap.put("Rhode Island", "RI");
        statesMap.put("South Carolina", "SC");
        statesMap.put("South Dakota", "SD");
        statesMap.put("Tennessee", "TN");
        statesMap.put("Texas", "TX");
        statesMap.put("Utah", "UT");
        statesMap.put("Virginia", "VA");
        statesMap.put("Vermont", "VT");
        statesMap.put("Washington", "WA");
        statesMap.put("Wisconsin", "WI");
        statesMap.put("West Virginia", "WV");
        statesMap.put("Wyoming", "WY");
        statesMap.put("Australian Capital Territory", "ACT");
        statesMap.put("New South Wales", "NSW");
        statesMap.put("Northern Territory", "NT");
        statesMap.put("Queensland", "QLD");
        statesMap.put("South Australia", "SA");
        statesMap.put("Tasmania", "TAS");
        statesMap.put("Victoria", "VIC");
        statesMap.put("Western Australia", "WA");
        statesMap.put("Alberta", "AB");
        statesMap.put("British Columbia", "BC");
        statesMap.put("Manitoba", "MB");
        statesMap.put("New Brunswick", "NB");
        statesMap.put("Newfoundland and Labrador", "NL");
        statesMap.put("Nova Scotia", "NS");
        statesMap.put("Northwest Terr.", "NT");
        statesMap.put("Nunavut", "NU");
        statesMap.put("Ontario", "ON");
        statesMap.put("Prince Edward Island", "PE");
        statesMap.put("Quebec", "QC");
        statesMap.put("Saskatchewan", "SK");
        statesMap.put("Yukon Territory", "YT");
        return statesMap.get(state);
    }

    public static String getEventName(String eventCode) {
        Map<String, String> eventNameAndCode = new HashMap<>();
        eventNameAndCode.put("k22newyork", "newyork");
        eventNameAndCode.put("k22netherlands", "netherlands");
        eventNameAndCode.put("k22lasvegas", "lasvegas");
        eventNameAndCode.put("k22sydney", "australia");
        return eventNameAndCode.get(eventCode) != null ? eventNameAndCode.get(eventCode) : "newyork";
    }

    public static String getPageURIWithStateAndViewName(Event event, String viewName) {
        return "https://knowledge.servicenow.com/" + event.getEventName() + "/" + viewName + "?state=" + event.getEventCode() + "." + viewName +
                "&redirect_uri=https%3A%2F%2Fknowledge.servicenow.com%2Flibrary";
    }
}
