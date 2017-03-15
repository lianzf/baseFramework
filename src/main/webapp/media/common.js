function constructDictionaryOption(key, value, selectId, description) {
	$.ajax({
		type : 'POST',
		url : "../dictionary/getDicByKey",
		dataType : "json",
		async : false,
		data : {
			key : key,
			value : value
		},
		success : function(respData) {
			var option = "<option value=''>" + description + "</option>";
			$.each(respData, function(tindex, telement) {
				if (telement.id == value) {
					option += "<option selected value='" + telement.id + "'>"
							+ telement.name + "</option>";
				} else {
					option += "<option value='" + telement.id + "'>"
							+ telement.name + "</option>";
				}
			});
			document.getElementById(selectId).innerHTML = option;
		}
	});
}

function constructDictionaryOption1(key, value, selectId, description) {
	var s;
	$.ajax({
		type : 'POST',
		url : "../dictionary/getDicByKey",
		dataType : "json",
		async : false,
		data : {
			key : key,
			value : value
		},
		success : function(obj) {
			s = obj;
		}
	});
	return s;
}

function getWebsite(id) {
	var option = "<option value=''>选择来源</option>";
	$.ajax({
		type : 'POST',
		url : "../website/getAllWebsite",
		dataType : "json",
		async : false,
		success : function(respData) {
			$.each(respData, function(tindex, telement) {
				if (telement.id == id) {
					option += "<option selected value='" + telement.id + "'>"
							+ telement.websiteName + "</option>";
				} else {
					option += "<option value='" + telement.id + "'>"
							+ telement.websiteName + "</option>";
				}
			});
		}
	});
	return option;
}

function cutString(str, len) {
	// length属性读出来的汉字长度为1
	if (str.length * 2 <= len) {
		return str;
	}
	var strlen = 0;
	var s = "";
	for (var i = 0; i < str.length; i++) {
		s = s + str.charAt(i);
		if (str.charCodeAt(i) > 128) {
			strlen = strlen + 2;
			if (strlen >= len) {
				return s.substring(0, s.length - 1) + "...";
			}
		} else {
			strlen = strlen + 1;
			if (strlen >= len) {
				return s.substring(0, s.length - 2) + "...";
			}
		}
	}
	return s;
}

/**
 * 将秒数换成时分秒格式
 */
function formatSeconds(value) {
    var theTime = parseInt(value);// 秒
    var hourTime = parseInt(theTime / 3600);//小时
    var minuteTime = parseInt((theTime%3600)/60);//分钟
    var secondTime = parseInt(theTime%3600%60);//秒
    var result = '';
    result += (hourTime < 10 ? '0'+hourTime : hourTime);
    result += (':' + (minuteTime < 10 ? '0'+minuteTime : minuteTime));
    result += (':' + (secondTime < 10 ? '0'+secondTime : secondTime));
    return result;
}
