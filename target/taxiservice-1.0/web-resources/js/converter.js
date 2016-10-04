
function todms()
{
    var Slat ="";
    var Slong ="";

    var LA = parseFloat( document.Lat.value );
    var LN = parseFloat( document.Lng.value );

    Slat = Math.floor(LA);
    Slong = Math.floor(LN);

    Slat = Slat+"&deg;";
    Slong = Slong+"&deg;";

    var LAdeg = LA.toString().split(".");
    var LNdeg = LN.toString().split(".");

    var LAminutes = ("0." + LAdeg[1])*60;
    var LNminutes = ("0." + LNdeg[1])*60;

    var LAm = LAminutes;
    var LNm = LNminutes;

    LAm = LAminutes.toString().split(".");
    LNm = LNminutes.toString().split(".");

    if(isNaN(LAm[0])) LAm[0]=0;
    if(isNaN(LNm[0])) LNm[0]=0;

    Slat=Slat+LAm[0]+"&prime;";
    Slong=Slong+LNm[0]+"&prime;";

    var LAs = ("0." + LAm[1]) * 60;
    var LNs = ("0." + LNm[1]) * 60;

    if(isNaN(LAs)) LAs=0;
    if(isNaN(LNs)) LNs=0;

    Slat = Slat+ Math.round( LAs ) +"&Prime;";
    Slong = Slong + Math.round( LNs ) +"&Prime;";

    // if (document.f1.selLA.value=="-")Slat="-"+Slat;
    // if (document.f1.selLN.value=="-")Slong="-"+Slong;
    //
    // if (document.f1.selLA.value=="-")LA=-LA;
    // if (document.f1.selLN.value=="-")LN=-LN;


    this.Lat.innerHTML= Slat;
    this.Lng.innerHTML= Slong;

//var S = "http://maps.google.com/?ll="+ LA+","+LN;
    var S = "http://www.google.com/maps?q="+ LA+",+"+LN;
    document.getElementById('url').innerHTML = "<a href='"+S+"'>"+S+"<\/a>";
}
