//
//  MainContentView.swift
//  IdealoLine
//
//  Created by Igor Berehovyi (RIT Student) on 12/3/22.
//

import SwiftUI

enum Tabs: Hashable {
    case list
    case map
}

struct MainContentView: View {
    
    @State private var selectedTab = Tabs.list
    
    var body: some View {
        TabView(selection: $selectedTab){
            ExploreView()
                .tabItem {
                    Image(systemName: "house.fill")
                    Text("Explore")
                }
                .tag(Tabs.map)
            
            CartView()
                .tabItem {
                    Image(systemName: "heart.fill")
                    Text("Favourite")
                }
        } //TabView
        .navigationBarBackButtonHidden(true)
    }
}

struct MainContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
