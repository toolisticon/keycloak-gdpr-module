var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "8854",
        "ok": "0",
        "ko": "8854"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "479",
        "ok": "-",
        "ko": "479"
    },
    "meanResponseTime": {
        "total": "4",
        "ok": "-",
        "ko": "4"
    },
    "standardDeviation": {
        "total": "20",
        "ok": "-",
        "ko": "20"
    },
    "percentiles1": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "percentiles2": {
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "percentiles3": {
        "total": "5",
        "ok": "-",
        "ko": "5"
    },
    "percentiles4": {
        "total": "11",
        "ok": "-",
        "ko": "11"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "count": 8854,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "14.906",
        "ok": "-",
        "ko": "14.906"
    }
},
contents: {
"req_encrypt-small-t-30791": {
        type: "REQUEST",
        name: "encrypt_small_text",
path: "encrypt_small_text",
pathFormatted: "req_encrypt-small-t-30791",
stats: {
    "name": "encrypt_small_text",
    "numberOfRequests": {
        "total": "4427",
        "ok": "0",
        "ko": "4427"
    },
    "minResponseTime": {
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "maxResponseTime": {
        "total": "479",
        "ok": "-",
        "ko": "479"
    },
    "meanResponseTime": {
        "total": "5",
        "ok": "-",
        "ko": "5"
    },
    "standardDeviation": {
        "total": "27",
        "ok": "-",
        "ko": "27"
    },
    "percentiles1": {
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "percentiles2": {
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "percentiles3": {
        "total": "5",
        "ok": "-",
        "ko": "5"
    },
    "percentiles4": {
        "total": "11",
        "ok": "-",
        "ko": "11"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "count": 4427,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "7.453",
        "ok": "-",
        "ko": "7.453"
    }
}
    },"req_encrypt-big-tex-c7c28": {
        type: "REQUEST",
        name: "encrypt_big_text",
path: "encrypt_big_text",
pathFormatted: "req_encrypt-big-tex-c7c28",
stats: {
    "name": "encrypt_big_text",
    "numberOfRequests": {
        "total": "4427",
        "ok": "0",
        "ko": "4427"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "160",
        "ok": "-",
        "ko": "160"
    },
    "meanResponseTime": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "standardDeviation": {
        "total": "8",
        "ok": "-",
        "ko": "8"
    },
    "percentiles1": {
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "percentiles2": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "percentiles3": {
        "total": "4",
        "ok": "-",
        "ko": "4"
    },
    "percentiles4": {
        "total": "11",
        "ok": "-",
        "ko": "11"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "count": 4427,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "7.453",
        "ok": "-",
        "ko": "7.453"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
