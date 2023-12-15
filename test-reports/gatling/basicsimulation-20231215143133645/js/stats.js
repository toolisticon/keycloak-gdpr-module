var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "8934",
        "ok": "0",
        "ko": "8934"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "283",
        "ok": "-",
        "ko": "283"
    },
    "meanResponseTime": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "standardDeviation": {
        "total": "12",
        "ok": "-",
        "ko": "12"
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
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "percentiles4": {
        "total": "5",
        "ok": "-",
        "ko": "5"
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
    "count": 8934,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "15.04",
        "ok": "-",
        "ko": "15.04"
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
        "total": "4467",
        "ok": "0",
        "ko": "4467"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "283",
        "ok": "-",
        "ko": "283"
    },
    "meanResponseTime": {
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "standardDeviation": {
        "total": "16",
        "ok": "-",
        "ko": "16"
    },
    "percentiles1": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "percentiles2": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "percentiles3": {
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "percentiles4": {
        "total": "5",
        "ok": "-",
        "ko": "5"
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
    "count": 4467,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "7.52",
        "ok": "-",
        "ko": "7.52"
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
        "total": "4467",
        "ok": "0",
        "ko": "4467"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "68",
        "ok": "-",
        "ko": "68"
    },
    "meanResponseTime": {
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "standardDeviation": {
        "total": "3",
        "ok": "-",
        "ko": "3"
    },
    "percentiles1": {
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "percentiles2": {
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "percentiles3": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    },
    "percentiles4": {
        "total": "4",
        "ok": "-",
        "ko": "4"
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
    "count": 4467,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "7.52",
        "ok": "-",
        "ko": "7.52"
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
