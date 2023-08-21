//
//  CartView.swift
//  IdealoLine
//
//  Created by Igor Berehovyi (RIT Student) on 12/3/22.
//

import SwiftUI

struct CartView: View {
    @State var array = UserDefaults.standard.stringArray(forKey: "favorites") ?? [String]()

    private var searchResults: [ProductDetails] {
      let results = DataProvider.all()
        print(array)
      return results
    }

    var body: some View {
              List {
                  VStack(alignment: .leading){
                      ForEach(array, id: \.self) { result in
                          HStack(){
                              Text(result)
                          }.padding()
                          
                      }
                  }
                      }.refreshable {
                          array = UserDefaults.standard.stringArray(forKey: "favorites") ?? [String]()
                      }
    }
}

struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
