//
//  ExploreView.swift
//  IdealoLine
//
//  Created by Igor Berehovyi (RIT Student) on 12/3/22.
//

import SwiftUI

struct ExploreView: View {
    @State private var searchText: String = ""
    @State private var sortMode = 0
    
    private var searchResults: [ProductDetails] {
        let results = DataProvider.all()
        if (sortMode == 0 && searchText.isEmpty){
            return results
        } else if (sortMode == 0){
            return results.filter {
                $0.name.lowercased().contains(searchText.lowercased()) || $0.emoji.contains(searchText)
            }
        } else if (sortMode == 1){
            return results.sorted(by: { $0.price < $1.price })
        } else {
            return results.sorted(by: { $0.price > $1.price })
        }
    }
    
    private var suggestions: [ProductDetails] {
        if searchText.isEmpty { return [] }
        return searchResults
    }
    
    var body: some View {
        NavigationView {
            VStack{
                Picker("Sort products", selection: $sortMode) {
                    Text("By search").tag(0)
                    Text("Price (lowest first)").tag(1)
                    Text("Price (highest first)").tag(2)
                }
                .onChange(of: sortMode) {
                    tag in print("Option: \(tag)")
                }
                
                List(searchResults) { product in
                    NavigationLink(destination: {
                        productDescriptionView(productDetails: product)
                    }){
                        HStack{
                            productImgView(image: product.imgName, type: product.type)
                                .padding(.trailing)
                            VStack(alignment: .leading){
                                Text(product.name)
                                Text(String(product.price) + "$")
                                    .font(.footnote)
                                    .foregroundColor(.gray)
                            }
                        }
                        .padding()
                    }
                }.listStyle(PlainListStyle())
                    .navigationTitle("Products")
                    .searchable(
                        text: $searchText,
                        placement: .navigationBarDrawer(displayMode: .always),
                        prompt: "Search for product"
                    ) {
                        if(sortMode == 0){
                            ForEach(suggestions) { product in
                                Text("Looking for \(product.name)?")
                                    .searchCompletion(product.name)
                            }
                        }
                    }
            }
        }
    }
}

struct productImgView: View {
    let image: Image?
    let type: String
    
    var body: some View {
        ZStack {
            if image != nil {
                image?
                    .resizable()
            } else {
                Color(.systemIndigo)
                if(type == "camera"){
                    Image(systemName: "camera.fill")
                        .font(.largeTitle)
                        .foregroundColor(.white)
                }else if(type == "phone"){
                    Image(systemName: "phone")
                        .font(.largeTitle)
                        .foregroundColor(.white)
                }else{
                    Image(systemName: "phone")
                        .font(.largeTitle)
                        .foregroundColor(.white)
                }
                
            }
        }
        .frame(width: 50, height: 50)
        .shadow(radius: 5)
        .padding(.trailing, 5)
    }
}

struct productDescriptionView: View {
    
    @State private var array = (UserDefaults.standard.array(forKey: "favorites") ?? []) as! [String]
    let productDetails: ProductDetails
    
    var body: some View {
        VStack {
            HStack {
                Text("\(productDetails.emoji) \(productDetails.name)")
                    .font(.largeTitle)
                    .bold()
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            
            productDetails.imgName?
                .resizable()
                .scaledToFit()
                .frame(width: 300, height: 300)
            
            VStack{
                Text(productDetails.description)
                    .padding(.top, 20)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                HStack{
                    Text(String(productDetails.price) + "$")
                        .foregroundColor(.red)
                        .font(.largeTitle)
                    
                    Spacer()
                    
                    Button {
                        print("pressed")
                        if !array.contains(productDetails.name) {
                            array.append(productDetails.name)
                        } else {
                            let index = array.firstIndex(of: productDetails.name)
                            array.remove(at: index ?? Int())
                            
                        }
                        UserDefaults.standard.set(array, forKey:"favorites")
                        
                        print(array)
                    }label:{
                        if(!array.contains(productDetails.name)){
                            Image(systemName: "heart")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 30, height: 30)
                        } else {
                            Image(systemName: "heart.fill")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 30, height: 30)
                        }
                    }
                }.padding(.top, 35)
            }
            
            Spacer()
        }.padding([.leading, .trailing], 24)
    }
}

struct ExploreView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
