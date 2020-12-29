$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
        colModel: [
            {label: '表名', name: 'tableName', width: 100, key: true},
            {label: 'Engine', name: 'ENGINE', width: 70},
            {label: '表备注', name: 'tableComment', width: 100, editable: true},
            {label: '创建时间', name: 'createTime', width: 100}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50, 100],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "record",
            page: "current",
            // total: "total",
            // records: "pages"
            total: "pages",
            records: "total"
        },
        prmNames: {
            page: "current",
            rows: "size",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },
        onCellSelect: function (cellContent) {
            $.get('sys/generator/table/conditions?tableName=' + cellContent,
                function (data){
                    // $('div:#generator_info').html(data);
                    // var data = '这是一个测试例子';
                    // document.getElementById('generator_info').innerHTML = data;
                    console.log(data)
                }
            )
        }
    });
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            tableName: null
        }
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'tableName': vm.q.tableName},
                page: 1
            }).trigger("reloadGrid");
        },
        generator: function () {
            var tableNames = getSelectedRows();
            if (tableNames == null) {
                return;
            }
            var moduleName = vm.q.moduleName;
            var packageName = vm.q.package;
            var author = vm.q.author;

            if (moduleName == undefined) {
                moduleName = "";
            }
            if (packageName == undefined) {
                packageName = "";
            }
            if (author == undefined) {
                author = "";
            }
            location.href = "sys/generator/code?tables=" + tableNames.join() + "&moduleName=" + moduleName + "&packageName=" + packageName + "&author=" + author;
        },
        _generator_info: function () {

        }
    }
});

