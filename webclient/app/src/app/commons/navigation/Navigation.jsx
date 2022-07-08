import React from "react";
import AppHeader from "./appHeader/AppHeader";
import SidebarNavigation from "./sidebarNavigation/SidebarNavigation";

function Navigation(props) {
    const {menuItems, switchLength, title, logo} = props;

    if (menuItems.length > switchLength) {
        return (
            <>
                <SidebarNavigation menuItems={menuItems} title={title} logo={logo}>
                    {props.children}
                </SidebarNavigation>
            </>
        )
    }

    return (
        <>
            <AppHeader menuItems={menuItems} title={title} logo={logo}/>
            {props.children}
        </>
    )

}

Navigation.defaultProps = {
    switchLength: 4,
    title: "New App",
    menuItems: []

}

export default Navigation;
