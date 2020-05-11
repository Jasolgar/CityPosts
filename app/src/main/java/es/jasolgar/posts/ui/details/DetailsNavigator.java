package es.jasolgar.posts.ui.details;

public interface DetailsNavigator {

    void launchMail(String mail);

    void launchGeoMaps(String lat, String lng);

    void launchPhoneCall(String phone);

    void loadWebUrl(String webUrl);

}
