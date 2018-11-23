var NavigationComp = {
	methods:{
		getMenus: function(result){
			let menus = result;
			if(isBlankArray(menus)){
				return;
			}
			let menu = "";
			let menuLi = "";
			let menuUL = this.getMenuUL();
			for(let i = 0 ; i < menus.length ; i++){
				menu = menus[i];
				menuLi = this.traverseMenu(menu);
				menuUL.append(menuLi);
			}
		},
		traverseMenu: function(menu){
			let menuNav = "";
			let children = menu.children;
			if(isBlankArray(children)){
				let li = this.renderMenu(menu);
				menuNav += li;
			}else{
				let number = RndNum(5);
				let li = '<li class="panel panel-default dropdown">';
				let dropA = '<a data-toggle="collapse" href="#dropdown-'+number+'">'
						          + '<span class="icon '+menu.iconClass+'"></span><span class="title">'+menu.name+'</span>'
						          + '</a>';
				li += dropA;
				let dropDiv = '<div id="dropdown-'+number+'" class="panel-collapse collapse">'
				                     + '<div class="panel-body">'
				                     + '<ul class="nav navbar-nav">';
				for(let i = 0 ; i < children.length ; i++){
					let child = children[i];
					dropDiv += this.traverseMenu(child);
				}
				dropDiv += '</ul></div></div>';
				li += dropDiv;
				li += '</li>';
				menuNav += li;
			}
			return menuNav;
		},
		renderMenu: function(menu){
			let li = '<li>';
			let a = isBlankStr(menu.href)  ? '<a href="javascript:void(0);">' : '<a data-tabId="'+menu.id+'" onclick="goToPage(this,\''+menu.href+'\',\''+menu.name+'\',\''+menu.id+'\',\''+menu.target+'\',\''+menu.parentNames+'\')" href="javascript:void(0);">';
			let span = '<span class="icon fa '+menu.iconClass+'"></span><span class="title">'+menu.name+'</span>';
			li += a;
			li += span;
			li += '</a>';
			li += '</li>';
			return li;
		},
		goToPage: function(option,pageUrl,title,menuId,target,parentNames) {
			//左侧菜单显示当前操作菜单的顶部菜单（高亮）
			this.changeActiveMenu(option);
			//顶部导航显示当前操作菜单
			if(target != '1'){
				this.setTopActiveMeunTitle(parentNames,title);
			}
			//跳转页面
			this.viewPage(pageUrl,title,menuId,target);
		},
		viewPage: function(pageUrl,title,menuId, target){
			if(isBlankStr(pageUrl)){
				return;
			}
			pageUrl = $.trim(pageUrl);
			pageUrl = (pageUrl.indexOf("/") == 0 ? pageUrl.substring(1) : pageUrl);
			if(target == '1'){
				window.open(contextPath + "/" + pageUrl);
			}else{
			    $('#mainFrame').find('iframe').attr('src',contextPath + "/" + pageUrl);
			}
		},
		setTopActiveMeunTitle: function(parentNames,title){
			let activeMenu = $('#activeMenu');
			activeMenu.children().remove();
			if(isBlankStr(parentNames)){
				activeMenu.append('<li class="active" style="font-size:16px;">'+title+'</li>');
			}else{
				let pNames = parentNames.split(',');
				let length = pNames.length;
				for(let i = 0; i < length ; i++){
					activeMenu.append('<li style="font-size:16px;">'+pNames[i]+'</li>');
				}
				activeMenu.append('<li class="active" style="font-size:16px;">'+title+'</li>');
			}
		},
		changeActiveMenu: function(option){
			this.getMenuUL().children("li.active").removeClass("active");
			let dropDiv = $(option).closest(".panel-collapse");
			if(dropDiv.length == 1){
				$(dropDiv).parent("li.dropdown").addClass("active");
			}else{
				$(option).closest("li").addClass("active");
			}
		},
		getMenuUL: function(){
			return $("#menuUL");
		}
	}
}