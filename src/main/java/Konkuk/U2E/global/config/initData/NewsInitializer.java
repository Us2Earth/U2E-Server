package Konkuk.U2E.global.config.initData;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Konkuk.U2E.domain.news.domain.ClimateProblem.*;

@Component
@RequiredArgsConstructor
@Getter
public class NewsInitializer {
    private final NewsRepository newsRepository;
    private final List<News> newsList = new ArrayList<>();

    private final List<String> urls = List.of(
            "https://www.bbc.com/news/articles/cx265w8v16qo", "https://www.bbc.com/news/articles/c5yl5g58k17o", "https://www.bbc.com/news/articles/cg41y4krxvno",
            "https://www.bbc.com/news/articles/cgkgyz0444ko", "https://www.bbc.com/news/articles/c4g3e35y3qqo", "https://www.bbc.com/news/articles/c5y214x9lw1o",
            "https://www.bbc.com/news/articles/c1lp365pd1go", "https://www.bbc.com/news/articles/c62kp3dk17lo", "https://www.bbc.com/news/articles/cnvz8ljzz8ro",
            "https://www.bbc.com/news/articles/cdx2801qegvo", "https://www.bbc.com/news/articles/crmjr3xz2kyo", "https://www.bbc.com/news/articles/c4gm3917pgyo",
            "https://www.bbc.com/news/articles/c86p2n6ggx5o", "https://www.bbc.com/news/articles/cm2y082ekydo", "https://www.bbc.com/news/articles/ckg10yqzez9o",
            "https://www.bbc.com/news/articles/clynpywddr1o", "https://www.bbc.com/news/articles/ckg84x797q8o", "https://www.bbc.com/news/articles/c8d487epdl9o",
            "https://www.bbc.com/news/articles/cevx7lkznm1o", "https://www.bbc.com/news/articles/cly6gn134jvo"
    );

    private final List<String> imageUrls = List.of(
            "https://ichef.bbci.co.uk/news/1536/cpsprodpb/dc84/live/c1272b10-0f08-11f0-ac9f-c37d6fd89579.png.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/c59e/live/2fc21290-0eb6-11f0-aabc-a51f61430e55.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/610b/live/c73b11b0-0e13-11f0-ac9f-c37d6fd89579.jpg.webp",
            "https://ichef.bbci.co.uk/news/1536/cpsprodpb/59d2/live/7d553c60-0d78-11f0-a1c5-cd0d1898b025.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/cdef/live/c2dec910-0d3a-11f0-b1c8-05eb2b6add27.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/39cb/live/2b01f1c0-0aed-11f0-94d4-6f954f5dcfa3.png.webp",
            "https://ichef.bbci.co.uk/news/1536/cpsprodpb/813d/live/b13d3b60-0a10-11f0-a457-1fbea9af81fd.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/a5f7/live/d942b390-b25b-11ef-abc8-5d4f677f13f2.png.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/e546/live/7d9b7c10-03d3-11f0-9b36-09a73999182b.jpg.webp",
            "", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/a79c/live/09987040-0679-11f0-8777-f3a97186d396.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/4a45/live/869a9d10-0652-11f0-996d-45b3433501bf.jpg.webp",
            "https://ichef.bbci.co.uk/news/1536/cpsprodpb/a2c8/live/87508430-05d1-11f0-8751-a7ef8f090311.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/6c34/live/05469cd0-057d-11f0-88b7-5556e7b55c5e.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/ea25/live/17724120-057d-11f0-8380-1b78bad7f910.jpg.webp",
            "https://ichef.bbci.co.uk/news/1536/cpsprodpb/56e7/live/18255e30-04b1-11f0-88b7-5556e7b55c5e.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/39a7/live/0a4c2470-04fb-11f0-b61c-853344875b4d.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/ad14/live/c2742650-03f8-11f0-88b7-5556e7b55c5e.jpg.webp",
            "https://ichef.bbci.co.uk/news/1536/cpsprodpb/117d/live/109d0ed0-026b-11f0-b50e-9d086302645f.jpg.webp", "https://ichef.bbci.co.uk/news/1536/cpsprodpb/1879/live/1332fc30-ff5b-11ef-ac3e-f94317684f61.jpg.webp"

    );

    private final List<String> titles = List.of(
            "UK Meteorological Office Issues Wildfire Warning",
            "Indian Meteorological Department Warns of Intense Summer Heatwave",
            "Wildfire Alert Issued Amid Dry Weather Conditions",

            "Flood Prevention Projects in Devon and Cornwall Receive Increased Government Funding",
            "South Korean Man Investigated for Arson While Tending Ancestral Grave",
            "South Korea Battles to Save Lives and Heritage Amid Wildfires",

            "Worst Wildfire in South Korean History Leaves at Least 24 Dead",
            "New Sewage Storage Tank Installed in Chippenham, Wiltshire for Flood Prevention",
            "Fallen Trees from Storms Help Restore Natural Ecosystems",

            "At Least 2 Dead in South Korean Wildfires... National Disaster Declared",
            "15 Fire Brigades Deployed to Combat Marsden Moor Wildfire in the UK",
            "Wildfire Near Inverness Amid 'Extreme Risk' Warning Across Scotland",

            "'Extreme' Wildfire Warning in Effect as Firefighting Continues in West Dunbartonshire, Scotland",
            "First Wildfire in Cumbria, UK Nearly Became Catastrophic... Firefighters Warn of Lack of Management",
            "Dorset Expands PSPO to Prevent Wildfires... Crackdown on Open Flame Activities",

            "£34 Million Invested to Address Risk of Colliery Slag Heap Collapse in Wales",
            "Amber Alert Issued for Wildfire Risk in Northern Ireland... Aftermath of Recent Moor Fire",
            "‘Extreme’ Wildfire Warning Across Scotland... Residents Advised to Stay Alert During Dry Weather",

            "At Least 34 Dead in Southern US Due to Tornadoes and Dust Storms... Hundreds of Homes Damaged, Wildfires Spreading",
            "UK Rural Communities Demand Better Connectivity After Storm Eowyn Disrupts Communication"
    );

    private final List<String> bodies = List.of(
            "The UK Met Office has issued an amber wildfire warning, prompting the London Fire Brigade to urge citizens to exercise caution. Although recent temperatures haven’t been as high as in summer, the dry spring weather means that even a small spark can ignite grass, shrubs, or litter, leading to wildfires that can spread rapidly. Paul McCourt of the London Fire Brigade referenced cases in Los Angeles and Korea, warning that fires can spread quickly outdoors even without high temperatures. He advised citizens to avoid barbecuing outdoors or on balconies, dispose of trash properly, and be careful with cigarette butts. Meteorologist Kathryn Chalk from the Met Office explained that strong winds and ongoing dry weather are increasing the likelihood of wildfires, with an amber warning currently in effect across the UK, especially in London. Temperatures are expected to reach 22°C this Friday.",
            "The India Meteorological Department has warned that this year will see higher-than-average temperatures and more heatwave days across India. Some regions have already experienced minimum temperatures 13°C above normal since February. From April to June, northern, eastern, central, and northwestern plains are expected to have 24 more heatwave days than usual. Uttar Pradesh, Jharkhand, Chhattisgarh, and Odisha could experience 10–11 heatwave days. In 2024, Churu in Rajasthan recorded a maximum temperature of 50.5°C. The Health Ministry reported 143 deaths due to heatwaves between March and June, but independent reports estimate around 733 deaths from heatstroke. The Meteorological Department noted that human activities like population growth, industrialization, and increased transportation are exacerbating heatwaves through greenhouse gas emissions, with India being the world’s third-largest emitter and still heavily reliant on coal power.",
            "Due to ongoing dry weather in Dorset and Wiltshire, the risk of wildfires has increased, leading to the issuance of an amber warning. The Dorset & Wiltshire Fire and Rescue Service (DWFRS) has advised citizens to avoid using disposable barbecues, lighting fires at campsites, and to be cautious with cigarette butts. This is the second warning issued this year and is expected to remain in effect throughout the week. Weather forecasts indicate no rain until early April, further increasing wildfire risks. In May 2020, a wildfire caused by a disposable barbecue burned 220 hectares of Wareham Forest, lasting over two weeks. Following this, a Public Space Protection Order (PSPO) was introduced in the area, and there are now plans to expand regulations imposing fines up to £100 for activities like barbecuing, using lanterns or fireworks, and littering with cigarettes in fields, grasslands, and forest areas. A public consultation on this matter is open until April 20.",
            "The UK Environment Agency has announced a £33 million (approximately 56 billion KRW) investment in flood prevention projects for Devon and Cornwall in southwest England. This funding will be allocated to 70 flood-related projects in the region, aiming to protect over 27,000 homes and commercial properties. Additionally, areas like Exmouth, Bampton, and Helston have been designated for protection against flooding, coastal erosion, and surface water overflow. For projects not included in this budget, the Environment Agency plans to continue collaborating with local authorities and flood response groups to mitigate risks.",
            "In Uiseong County, North Gyeongsang Province, South Korea, a 56-year-old man is under investigation for causing a major wildfire while tending to his ancestral grave. He is suspected of igniting branches with a lighter during the visit, leading to the fire’s spread. Although he has been booked, he has not been detained. His daughter stated that the fire started when her father attempted to burn overgrown branches. The flames spread rapidly due to dry weather, strong winds, pine-dominated forests, and warmer-than-average temperatures. Police, the Fire Agency, and the Forest Service are conducting a joint investigation, and the government has pledged financial support for the victims. ",
            "The worst wildfire in South Korea’s history, which began in Sancheong County, southeastern Korea, has spread to over 10 cities and counties, resulting in 27 deaths and 32 injuries. Firefighters are making all-out efforts to protect lives and cultural heritage. Most victims are elderly individuals in their 60s and 70s, and the fire has spread to areas like Uiseong, Andong, and Cheongsong. Gounsa Temple has been completely destroyed, and national treasures from the Joseon Dynasty, as well as UNESCO World Heritage Sites like Hahoe Village and Byeongsan Seowon, are under threat. Artifacts such as wooden printing blocks and Buddhist paintings have been urgently evacuated. Lee Han-kyung, head of the government’s disaster response headquarters, stated that the reality of the climate crisis has never been more evident, emphasizing that climate change and human activities have compounded the disaster.",
            "In late March 2025, a massive wildfire in southeastern South Korea has resulted in at least 24 deaths and 26 injuries, including 12 serious cases, with over 23,000 people evacuated. The wildfire began on March 21 in Sancheong County and spread to Uiseong County, Gyeongbuk, Andong, Cheongsong, and Yeongyang. Dry weather and strong winds have exacerbated the flames, leading to the highest level of national disaster response. Thousands of firefighters, 5,000 military personnel, and U.S. helicopters have been deployed. In Uiseong, a helicopter crash resulted in the pilot’s death. The Meteorological Administration reported minimal rain forecasts, making firefighting efforts challenging, and the government has vowed to crack down on illegal burning and individual negligence. There have been 244 wildfires this year, a 2.4-fold increase compared to the same period last year. Acting President Han Duck-soo stated, “This wildfire is the most devastating disaster in Korean history, setting unprecedented records.",
            "In November 2024, Chippenham in Wiltshire, UK, suffered significant flood damage due to Storm Bert, which caused the River Avon to overflow. In response, a £2 million (approximately 3.4 billion KRW) flood prevention project was implemented, including the installation of a 250,000-liter underground storage tank to increase sewer capacity and reduce overflow during heavy rainfall. The project, led by Wessex Water under the YTL Group, plans to invest £580 million from 2025 to 2030 to reduce stormwater flooding, introduce nature-based solutions, and improve rainwater management. However, Wessex Water has requested a review by the Competition and Markets Authority (CMA), stating that the five-year investment budget approved by the regulator Ofwat is 17% short of their requirements.",
            "In December 2024, Storm Darragh brought strong winds, reaching up to 142 km/h, to Exmoor National Park in Devon, UK, causing numerous trees to fall. However, these fallen trees are now seen as beneficial to the local ecosystem. The Exmoor National Park Authority (ENPA) reported that dead and fallen trees have become habitats for insects, birds, bats, fungi, and other organisms, contributing to the restoration of natural biodiversity. The park emphasized that “climate-adaptive forest management,” focusing on climate resilience, flood management, protection of endangered species, and preservation of rare moss habitats, is a key strategy, rather than merely planting more trees.",
            "On March 23, 2025, a large wildfire broke out in southeastern South Korea, resulting in the deaths of two firefighters and the evacuation of hundreds of people. In Sancheong County, South Gyeongsang Province, two firefighters died, and two others are missing. The wildfire is spreading nationwide, with the Forest Service issuing “severe” wildfire warnings for 12 areas. At 6 p.m., the government declared a national disaster in Ulsan Metropolitan City and the Gyeongbuk and Gyeongnam regions. Approximately 200 residents from eight villages in Sancheong County and over 400 people in Uiseong County, North Gyeongsang Province, have been urgently evacuated.",

            "At 1:30 PM local time on Friday, March 21, 2025, a major wildfire broke out in the moorland area of Marsden, West Yorkshire, UK, mobilizing 15 fire brigades and support units. The fire began near March Haigh Reservoir and expanded over an area of 6 miles (10 km) by 3 miles (5 km), spreading toward Denholme in Greater Manchester. By around 7:20 PM, most fire crews had withdrawn, but four pump teams remained on site to protect buildings. No casualties have been reported yet, and the cause of the fire is still under investigation.",
            "On March 21, 2025, a major wildfire occurred in the dry grasslands near Inverness, Scotland. The country had been under an “extreme wildfire risk” warning for lowland areas following a week of dry and sunny weather. Fire authorities stated that the flames were mostly under control and that no casualties had been reported. The fire likely resulted from a combination of human negligence—such as unattended sparks, campfires, barbecues, discarded cigarette butts, and glass bottles—and dry, warm weather, strong winds, and low humidity. The Scottish Fire and Rescue Service (SFRS) warned that “wildfires are uncontrollable and unpredictable natural disasters, especially during the dry seasons of spring and autumn.”",
            "On March 21, 2025, while an “extreme wildfire risk” warning was in effect across Scotland’s lowlands, a wildfire in the West Kilpatrick Hills, West Dunbartonshire, was successfully extinguished. At the time of ignition, flames were visible from homes near Bowling. With temperatures expected to rise to 18°C, the wildfire risk level had been upgraded from “very high” on Tuesday to “extreme” on Friday. This reflects weather and soil conditions that effectively created a “powder keg” for fire outbreaks.",
            "On March 19, 2025, the first wildfire of the year occurred at Ralfland Common, west of Shap in Cumbria, UK. Fire authorities expressed concern, saying it “could have been much worse.” The fire scorched about 100m × 300m (328ft × 984ft) of land. Fire crews from Shap and Penrith, aided by local farmers, reached the site and extinguished the flames using beaters and portable extinguishers. Thankfully, no injuries were reported. Shap Fire Station warned that a lack of forest management in Cumbria’s uplands in recent years has led to the buildup of dead vegetation, increasing wildfire risks. They added that the current dead zones pose serious hazards rather than helping conservation.",
            "In Dorset, UK, legal efforts are underway to strengthen wildfire prevention measures. Dorset Council is discussing the expansion of Public Space Protection Orders (PSPOs), which are already in place in areas like Wareham Forest, to cover more nature reserves and coastal zones. If enforced, the order would impose a £100 fine for using barbecues, lanterns, or fireworks outdoors or discarding cigarette butts, and allow authorities to confiscate fire-risk items. Councillor Gill Taylor, who oversees environment and housing, stated that the order is a necessary step to protect nature and ensure public safety. A public consultation will continue until April 20.",
            "To address the risk of old coal tip collapses, the Welsh Government has announced a £34 million (approx. 58.5 billion KRW) investment. Wales has 2,573 coal waste tips, of which 360 are deemed to pose public safety threats and require regular inspections. Rhondda Cynon Taf contains the highest number of “Category D” high-risk tips and will receive £11.49 million of the budget. This follows the 2020 Tylorstown landslip, where 60,000 tons of waste buried a river. In response, a joint UK-Welsh Coal Tip Safety Taskforce was formed. As the 1966 Aberfan disaster-era law is considered outdated, a new legal framework—the Disused Mine and Quarry Tips Bill—has been submitted to the Welsh Parliament.",
            "On March 20, 2025, an amber wildfire alert was issued across Northern Ireland following a large gorse fire near the Mourne Mountains. Dry and windy weather has increased fire risk. The fire required 60 firefighters and seven fire engines and is suspected to be arson. The Natural Hazards Partnership raised the previous yellow alert to amber, and Northern Ireland Water urged the public to refrain from using disposable barbecues, dumping waste, or burning trash. Fire officials stressed that such wildfires are mostly preventable and caused by human negligence. They strongly advised against using open flames outdoors and warned that wildfires are fast-spreading, unpredictable, and harmful to biodiversity and the environment.",
            "On March 18, 2025, Scotland issued its first “extreme” wildfire warning of the year for its lowland areas. The Scottish Fire and Rescue Service (SFRS) said that the prolonged dry and sunny weather, with temperatures expected to reach 18°C by Friday, significantly increases wildfire risks. The warning was an upgrade from “very high,” already in place in central and southwestern regions. Fire Commander Murray Dalgleish warned that wildfires can cause severe damage to livestock, farmland, protected habitats, and local livelihoods, and urged the public to act responsibly.",
            "In mid-March 2025, deadly tornadoes, dust storms, and wildfires swept through southern US states, leaving at least 34 dead and hundreds of homes destroyed. In Missouri, 12 people died and 19 tornadoes were recorded in 25 counties. Entire homes were reduced to debris. In Texas and Kansas, dust storms caused multi-vehicle collisions involving over 50 cars, killing at least four and eight people respectively. Oklahoma reported more than 130 wildfires and over 112 burn injuries, with even the governor’s ranch destroyed. Known as “Tornado Alley,” Texas, Louisiana, Oklahoma, and Kansas frequently experience tornadoes due to their geography, and experts say these early events reflect shifting seasonal weather patterns.",
            "In January 2025, Storm Éowyn caused widespread power outages and communication breakdowns across rural areas of Cumbria, northern UK. Thousands of households were left in the dark, unable to contact the outside world. Local MPs have since urged telecom and mobile network providers to improve infrastructure. Industry group Mobile UK stated that efforts are already underway to boost network resilience, including developing self-powered base stations. Energy regulator Ofgem announced that its 2028–2032 regional investment plan will include infrastructure improvements to better withstand extreme weather events."
    );

    private final List<LocalDate> dates = List.of(
            LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 3, 31), LocalDate.of(2025, 3, 30), LocalDate.of(2025, 3, 27),
            LocalDate.of(2025, 3, 26), LocalDate.of(2025, 3, 26), LocalDate.of(2025, 3, 23),
            LocalDate.of(2025, 3, 23), LocalDate.of(2025, 3, 22), LocalDate.of(2025, 3, 21),
            LocalDate.of(2025, 3, 21), LocalDate.of(2025, 3, 20), LocalDate.of(2025, 3, 20),
            LocalDate.of(2025, 3, 20), LocalDate.of(2025, 3, 20), LocalDate.of(2025, 3, 18),
            LocalDate.of(2025, 3, 16), LocalDate.of(2025, 3, 15)
    );

    private final List<List<ClimateProblem>> problems = List.of(
            List.of(WILDFIRE),
            List.of(TEMPERATURE_RISE),
            List.of(WILDFIRE),
            List.of(HEAVY_RAIN_OR_FLOOD),
            List.of(HEAVY_RAIN_OR_FLOOD, TEMPERATURE_RISE, DROUGHT_OR_DESERTIFICATION),
            List.of(WILDFIRE, TEMPERATURE_RISE, DROUGHT_OR_DESERTIFICATION, BIODIVERSITY_LOSS),
            List.of(WILDFIRE, TEMPERATURE_RISE, DROUGHT_OR_DESERTIFICATION, BIODIVERSITY_LOSS),
            List.of(HEAVY_RAIN_OR_FLOOD, SEA_LEVEL_RISE),
            List.of(DEFORESTATION, BIODIVERSITY_LOSS, TYPHOON_OR_TORNADO),
            List.of(WILDFIRE, TEMPERATURE_RISE, DROUGHT_OR_DESERTIFICATION),
            List.of(WILDFIRE, DROUGHT_OR_DESERTIFICATION),
            List.of(WILDFIRE, DROUGHT_OR_DESERTIFICATION, TEMPERATURE_RISE),
            List.of(WILDFIRE, DROUGHT_OR_DESERTIFICATION, TEMPERATURE_RISE),
            List.of(WILDFIRE, DEFORESTATION, DROUGHT_OR_DESERTIFICATION),
            List.of(WILDFIRE, BIODIVERSITY_LOSS, DROUGHT_OR_DESERTIFICATION),
            List.of(HEAVY_RAIN_OR_FLOOD, SEA_LEVEL_RISE),
            List.of(WILDFIRE, FINE_DUST, DROUGHT_OR_DESERTIFICATION),
            List.of(WILDFIRE, TEMPERATURE_RISE, DROUGHT_OR_DESERTIFICATION),
            List.of(TYPHOON_OR_TORNADO, HEAVY_RAIN_OR_FLOOD, WILDFIRE, FINE_DUST, TEMPERATURE_RISE),
            List.of(TYPHOON_OR_TORNADO, HEAVY_RAIN_OR_FLOOD, TEMPERATURE_RISE)
    );

    public void initializeNews() {
        if (newsRepository.count() > 0) return;

        for (int i = 0; i < titles.size(); i++) {
            News news = News.builder()
                    .newsUrl(urls.get(i))
                    .imageUrl(imageUrls.get(i))
                    .newsTitle(titles.get(i))
                    .newsBody(bodies.get(i))
                    .newsDate(dates.get(i))
                    .build();

            List<Climate> climates = problems.get(i).stream()
                    .map(problem -> new Climate(problem, news))
                    .toList();

            news.setClimateList(climates);

            newsRepository.save(news);
            newsList.add(news);
        }
    }

}