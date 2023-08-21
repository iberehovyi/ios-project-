//
//  DataProvider.swift
//  IdealoLine
//
//  Created by Igor Berehovyi (RIT Student) on 12/3/22.
//

import Foundation
import SwiftUI

public struct DataProvider {
    static func all() -> [ProductDetails] {
        return [
            ProductDetails(
                type: "phone",
                imgName: Image("Samsung"),
                emoji: "📱",
                name: "Samsung",
                description: "Infinity-O zaslon A51 uređaja optimizira vizualnu simetriju. Sada možete gejmati, gledati, surfati i multitaskati bez prekida na zaslonu širokog formata dijagonale 6,5“ i FHD+ razlučivosti - a sve to omogućuje Vam Super AMOLED tehnologija. Uživajte u iskustvu upotrebe pametnog telefona koji okvir zaslona svodi na najmanju moguću mjeru i daje najviše zaslonskog prostora po svakom inču.",
                price: 899),
            ProductDetails(
                type: "phone",
                imgName: Image("iphone"),
                emoji: "📱",
                name: "IPhone",
                description: "6,1 Super Retina XDR Display Fortschrittliches Kamera System für bessere Fotos bei jedem Licht",
                price: 344),
            ProductDetails(
                type: "phone",
                imgName: nil,
                emoji: "📱",
                name: "Lenovo",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates",
                price: 211),
            ProductDetails(
                type: "phone",
                imgName: Image("nokia"),
                emoji: "📱",
                name: "Nokia",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "camera",
                imgName: Image("camera-canon"),
                emoji: "📸",
                name: "Canon",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "camera",
                imgName: nil,
                emoji: "📸",
                name: "Kodak",
                description: "ALorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 50),
            ProductDetails(
                type: "camera",
                imgName: Image("nikon"),
                emoji: "📸",
                name: "Nikon",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "camera",
                imgName: Image("sony"),
                emoji: "📸",
                name: "Sony",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "phone",
                imgName: nil,
                emoji: "📱",
                name: "Xiaomi",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "phone",
                imgName: nil,
                emoji: "📱",
                name: "Tecno",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "phone",
                imgName: nil,
                emoji: "📱",
                name: "OPPO",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
            ProductDetails(
                type: "phone",
                imgName: nil,
                emoji: "📱",
                name: "Huawei",
                description: "Lorem ipsum dolor sit amet. Qui accusamus modi qui provident tenetur sit voluptatem magni sed voluptas sunt qui sunt dolor vel recusandae aliquam. Et doloremque optio qui ipsam recusandae eos minus incidunt aut numquam quia ut nobis quae ut odio voluptates?",
                price: 1200),
        ]
    }
}

struct ProductDetails: Identifiable {
    let id = UUID()
    let type: String
    let imgName: Image?
    let emoji: String
    let name: String
    let description: String
    let price: Int
}
