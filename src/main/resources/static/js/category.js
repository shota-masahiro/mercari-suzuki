$(function() {
 
    // プルダウンのoption内容をコピー
    var pd2 = $("#mediumCategory option").clone();
    var pd3 = $("#smallCategory option").clone();
 
    // 1→2連動
    $("#largeCategory").change(function () {
        // largeCategoryのvalue取得
        var largeVal = $("#largeCategory").val();
 
        // mediumCategoryのdisabled解除
        $("#mediumCategory").removeAttr("disabled");
 
        // 一旦、一旦mediumCategoryのoptionを削除
        $("#mediumCategory option").remove();
 
        // copy済みの元のmediumCategoryを表示
        $(pd2).appendTo("#mediumCategory");
 
        // mediumCategoryクラスのoptionを削除
        $("#mediumCategory option[class != "+largeVal+"]").remove();
 
        // mediumCategory先頭表示
        $("#mediumCategory").prepend('<option value="" selected="selected">-- mediumCategory --</option>');
 
        // smallCategory disabled処理
        $("#smallCategory").attr("disabled", "disabled");
        $("#smallCategory option").remove();
        $("#smallCategory").prepend('<option value="---">-- smallCategory --</option>');
    });

    // 2→3連動
    $("#mediumCategory").change(function () {
        // mediumCategoryのvalue取得
        var mediumVal = $("#mediumCategory").val();
 
        // smallCategoryのdisabled解除
        $("#smallCategory").removeAttr("disabled");
 
        // 一旦、一旦smallCategoryのoptionを削除
        $("#smallCategory option").remove();

        // copy済みの元のsmallCategoryを表示
        $(pd3).appendTo("#smallCategory").val();
 
        // smallCategoryクラスのoptionを削除
        $("#smallCategory option[class != "+mediumVal+"]").remove();
 
        // smallCategory先頭表示
        $("#smallCategory").prepend('<option value="" selected="selected">-- smallCategory --</option>');
    });
});