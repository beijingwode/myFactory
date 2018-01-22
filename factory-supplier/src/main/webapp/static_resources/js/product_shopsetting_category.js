
    function switcher(dom, parent) {
        var targetName = "[nodefor='" + parent + "']";
        if ($(dom).attr('state') == 'on') {
            $(dom).attr('state', 'off');
            $(dom).removeClass('deicon').addClass('adicon');
            $(targetName).hide();
        } else {
            $(dom).attr('state', 'on');
            $(dom).removeClass('adicon').addClass('deicon');
            $(targetName).show();
        }
    }

    function switchState(dom) {
        if ($(dom).attr('state') == 'true') {
            $(dom).attr('state', false);
            $(dom).find('img').attr("src", staticResources+'images/off.png');
        } else {
            $(dom).attr('state', true);
            $(dom).find('img').attr("src", staticResources+'images/on.png');
        }
    }
    function addChild(dom, id) {
        $(dom).parent().parent().after($('<li class="newCat" nodefor="' + id + '" parent="' + id + '"><div class="mana01"><span><input class="redio" type="checkbox" style="visibility: hidden"  /></span><i class="micon"></i><input class="pubilc_input f148" type="text" value=""></div><div class="mana02"></div><div class="mana03"></div><div class="mana04"><a href="javascript:;" onclick="deleteThis(this);">删除</a></div></li>'));
    }
    function addCategory() {
        $(".manage_list").prepend($('<li class="newCat"><div class="mana01"><span><input class="redio" type="checkbox" style="visibility: hidden" /></span><input class="pubilc_input f148"  type="text" value=""></div><div class="mana02"></div><div class="mana03"></div><div class="mana04"><a href="javascript:;" onclick="deleteThis(this);">删除</a></div></li>'));
    }
    function deleteThis(dom) {
        $(dom).parent().parent().remove();
    }
    function saveCats() {
        if (saveStatus == 0) {
            var i = 0, j = 0, saveable = true;
            $(".newCat").each(function (index) {
                var nameDom = $($(this).find('input')[1]), name = $.trim(nameDom.val());
                nameDom.val(name);
                if (name.length > 20) {
                    showInfoBox("类别名称不能超过20个字符!", function () {
                        nameDom.focus();
                    });
                    saveable = false;
                    return false;
                } else if (name !== "") {
                    var cat = {"name": name, "supplierId": supplier};
                    if ($(this).attr('parent')) {
                        cat["parent"] = $(this).attr('parent');
                        cat["grade"] = 2;
                        cat["orders"] = parseInt(page) * 100 + j;
                        j++;
                    } else {
                        cat["grade"] = 1;
                        cat["orders"] = parseInt(page) * 100 + i;
                        i++;
                    }
                    categories[index] = cat;
                }
            });
            $(".manage_list li").not('[class]').each(function (index) {
                var nameDom = $(this).find('[type="text"]'), name = $.trim(nameDom.val()), state = ($(this).find('.mana05').attr("state") == 'true');
                if (name.length > 20) {
                    showInfoBox("类别名称不能超过20个字符!", function () {
                        nameDom.focus();
                    });
                    saveable = false;
                    i = 0;
                    j = 0;
                    return false;
                } else if (name != '') {
                    var cat = {"name": name, "id": $(this).find(':checkbox').val()};
                    if (!$(this).attr('nodefor')) {
                        cat["orders"] = parseInt(page) * 100 + i;
                        cat["isExpanding"] = state;
                        i++;
                    } else {
                        cat["orders"] = parseInt(page) * 100 + j;
                        j++;
                    }
                    updates[index] = cat;
                }
            });
            if (saveable) {
                var cats = JSON.stringify(categories), ups = JSON.stringify(updates);
                $.post("save.html", {cats: cats, ups: ups}, function (data) {
                    if (data === "success") {
                        showInfoBox("保存成功!", function () {
                            window.location.reload(true);
                        });
                    }
                });
                saveStatus = 1;
            }
            saveable = true;
        }
    }
    function collapseAll() {
        $('div [state]').each(function () {
            $(this).attr("state", false);
            $(this).find('img').attr("src", staticResources+'images/off.png');
        });
    }
    function expandAll() {
        $('div [state]').each(function () {
            $(this).attr("state", true);
            $(this).find('img').attr("src", staticResources+'images/on.png');
        });
    }
    function selectAll(dom) {
        if ($(dom).is(":checked")) {
            $(":checkbox").attr("checked", "true");
        } else {
            $(":checkbox").removeAttr("checked");
        }
    }
    function listenParent(dom) {
        var child = '[nodefor="' + $(dom).val() + '"]';
        if (!$(dom).is(":checked")) {
            $(child).find(":checkbox").removeAttr("checked");
        } else {
            $(child).find(":checkbox").attr("checked", "true");
        }
    }
    function listenChild(dom) {
        if (!$(dom).is(":checked")) {
            var parent = $(dom).parent().parent().parent().prevAll("li").not('[nodefor]')[0];
            $(parent).find(":checkbox").removeAttr("checked");
        }
    }
    function delAll() {
        var i = 0, deletes = [];
        $(".redio").each(function () {
            if ($(this).is(":checked")) {
                if (!isNaN($(this).val())) {
                    deletes[i] = $(this).val();
                    i++;
                }
            }
        });
        if (deletes.length > 0)
            $.post("delete.html", {id: deletes.join(",")}, function (data) {
                showInfoBox("成功删除" + data + "个分类", function () {
                    window.location.reload(true);
                });
            });
        else
            showInfoBox("请先选择要删除的分类!");
    }
    function delCat(dom, cid) {
        $.post("delete.html", {id: cid}, function (data) {
            showInfoBox("删除成功!", function () {
                window.location.reload(true);
            });
        })
    }
    function move(dom, direction) {
        var li = $(dom).parent().parent(), parent = li.next().attr('nodefor'), childs, mark = li.clone(), moveable = true;
        if (parent) childs = '[nodefor="' + parent + '"]';
        switch (direction) {
            case 'down':
                if (li.nextAll('li').not('[nodefor]').length <= 0) moveable = false;
                if (moveable) {
                    move($(li.nextAll().not('[nodefor]')[0]).find(".mana03 span"), 'up');
                    //终止向后执行.
                    return false;
                }
                break;
            case 'up':
                if (li.prevAll('li').not('[nodefor]').length <= 0) moveable = false;
                if (moveable) {
                    $(li.prevAll().not('[nodefor]')[0]).before(mark);
                    li.remove();
                }
                break;
            case 'top':
                $($(".manage_list li").not('[class]')[0]).before(mark);
                li.remove();
                break;
            case 'bottom':
                $($(".manage_list li").not('[class]').last()).after(mark);
                li.remove();
                break;
            default :
        }
        if (parent && moveable)
            $(childs).each(function (index) {
                var tem = $(this).clone();
                mark.after(tem);
                mark = tem;
                $(this).remove();
            });
    }
    function childMove(dom, direction) {
        var li = $(dom).parent().parent(), parent = li.attr('nodefor'), siblings = '[nodefor="' + parent + '"]', mark = li.clone();
        switch (direction) {
            case 'down':
                if (li.nextAll(siblings).length > 0) {
                    li.next(siblings).after(mark);
                    li.remove();
                }

                break;
            case 'up':
                if (li.prevAll(siblings).length > 0) {
                    li.prev(siblings).before(mark);
                    li.remove();
                }
                break;
            case 'top':
                $($(siblings).first()).before(mark);
                li.remove();
                break;
            case 'bottom':
                $($(siblings).last()).after(mark);
                li.remove();
                break;
            default :
        }
    }
