import React from "react";
import AppHeader from "./appHeader/AppHeader";
import SidebarNavigation from "./sidebarNavigation/SidebarNavigation";

function Navigation(props) {
    const {menuItems, switchLength, title, logo} = props;

        return (
            <>
                <SidebarNavigation menuItems={menuItems} title={title} logo={logo}>
                    {props.children}
                </SidebarNavigation>
            </>
        )

}

Navigation.defaultProps = {
    switchLength: 4,
    title: "New App",
    menuItems: []

}

export default Navigation;
